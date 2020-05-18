
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support.expected_conditions import presence_of_element_located
import time
import json
from selenium.common.exceptions import NoSuchElementException 
import tempfile
import os

empresas = []

def normalize(word):
    if os.path.exists("temp-word.json"):
        os.remove("temp-word.json")
    jsonfile = open("temp-word.json", "w")
    jsonfile.write(json.dumps({"word": word.strip()}))
    jsonfile.close()
    
    jsonfile = open("temp-word.json", "r")
    jsonword = json.loads(jsonfile.read())
    word = jsonword["word"]
    jsonfile.close()
    os.remove("temp-word.json")
    return word

def obter_dados_das_empresas(driver):
    qtde_acoes = len(driver.find_element_by_tag_name("table").find_elements_by_tag_name("a"))
    i = 0
    scroll_height = 0
    print("Acessando dados de %d empresas " % (qtde_acoes / 2))
    while True:
        if i > 0:
            driver.switch_to.default_content()
            iframe = driver.find_element_by_tag_name("iframe")
            driver.switch_to.frame(iframe)
            driver.execute_script("window.scrollTo(0, %d)" % scroll_height)
        tabela_acoes = driver.find_element_by_tag_name("table")
        acoes_link = tabela_acoes.find_elements_by_tag_name("a")
        razao_social = normalize(acoes_link[i].text)
        print(razao_social)
        ja_capturado = False
        for e in empresas:
            if e["razao_social"] == razao_social:
                ja_capturado = True
                print("Empresa", razao_social, "já capturada")
                break
        if ja_capturado == False:
            acoes_link[i].click()
            time.sleep(5)
            obter_dados_da_empresa(driver, razao_social)
            driver.back()
            time.sleep(5)
        i = i + 2
        if i < qtde_acoes:
            scroll_height = scroll_height + 40            
        else:
            break
    print("Dados das empresa obtidos")
    
def obter_dados_da_empresa(driver, razao_social):
    iframe = driver.find_element_by_tag_name("iframe")
    driver.switch_to.frame(iframe)
    tabela_acao = driver.find_element_by_tag_name("table")
    linhas_tabela_acao = tabela_acao.find_elements_by_tag_name("tr")

    nome_empresa = normalize(linhas_tabela_acao[0].find_elements_by_tag_name("td")[1].text)
    cnpj = normalize(linhas_tabela_acao[2].find_elements_by_tag_name("td")[1].text)
    atividades = linhas_tabela_acao[3].find_elements_by_tag_name("td")[1].text
    atividades = list(normalize(a) for a in atividades.split(";"))
    classificacao_setorial = linhas_tabela_acao[4].find_elements_by_tag_name("td")[1].text
    classificacao_setorial = list(normalize(c) for c in classificacao_setorial.split("/"))
    
    codigosNegociacao = []
    driver.find_element_by_xpath("/html/body/div[2]/div[1]/ul/li[1]/div/table/tbody/tr[2]/td[2]/a[1]").click()
    links_codigos_acoes = tabela_acao.find_elements_by_class_name("LinkCodNeg")
    for acao in links_codigos_acoes:
        codigosNegociacao.append(normalize(acao.text))
    
    e = {
        "razao_social": razao_social, 
        "nome_empresa": nome_empresa, 
        "cnpj": cnpj, 
        "atividades": atividades,
        "classificacao_setorial": classificacao_setorial, 
        "codigos_negociacao": codigosNegociacao
    }
    print(json.dumps(e))
    empresas.append(e)

def acessar_dados_das_empresas(driver):
    iframe = driver.find_element_by_tag_name("iframe")
    driver.switch_to.frame(iframe)
    qtde_letras_links = len(driver.find_elements_by_class_name("letra"))
    i = 0
    print("Acessando dados de %d empresas por caracter" % qtde_letras_links)
    while True:
        print("Letra %d" % i)
        if i > 0:
            driver.switch_to.default_content()
            driver.execute_script("window.history.go(-1)")
            time.sleep(5)
            driver.switch_to.default_content()
            iframe = driver.find_element_by_tag_name("iframe")
            driver.switch_to.frame(iframe)        
        caracter_link = driver.find_elements_by_class_name("letra")    
        caracter_link[i].click()
        time.sleep(5)
        try:
            msg_sem_empresas = driver.find_element_by_class_name("alert-box")
            if "Empresa não encontrada" in msg_sem_empresas.text:
                print("Empresa não encontrada")
            else:
                obter_dados_das_empresas(driver)
        except NoSuchElementException:
            obter_dados_das_empresas(driver)
        i = i + 1
        if i >= qtde_letras_links:       
            break
    print("Dados da empresa obtidos por caracter")


def normalize_empresas_json_and_save():
    jsonfile = open("gen-dados-empresas-b3-output.json", "w")
    jsonfile.write(json.dumps(empresas))
    jsonfile.close()

    jsonfile = open("gen-dados-empresas-b3-output.json", "r")
    empresas_temp = json.loads(jsonfile.read())
    jsonfile.close()

    razoes_social = {}
    empresas_normalized = []
    for e in empresas_temp:
        if e["razao_social"] not in razoes_social:
            empresas_normalized.append(e)
            razoes_social[e["razao_social"]] = 1

    jsonfile = open("gen-dados-empresas-b3-output.json", "w")
    jsonfile.write(json.dumps(empresas_normalized))
    jsonfile.close()


with webdriver.Firefox() as driver:
    try:
        jsonfile = open("gen-dados-empresas-b3-output.json", "r")
        empresas = json.loads(jsonfile.read())
        jsonfile.close()
    except Exception as err:
        print("Arquivo pré-existente não encontrado")
        print(err)

    driver.implicitly_wait(5)
    wait = WebDriverWait(driver, 10)    
    driver.get("http://www.b3.com.br/pt_br/produtos-e-servicos/negociacao/renda-variavel/empresas-listadas.htm")    
    driver.maximize_window()
    time.sleep(5)
    try:
        acessar_dados_das_empresas(driver)
    except Exception as err:
        print('Erro enquanto capturava os dados')
        print(err)

    normalize_empresas_json_and_save()    
    print("Arquivo final gerado!")
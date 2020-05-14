
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support.expected_conditions import presence_of_element_located
import time
from selenium.common.exceptions import NoSuchElementException 

   

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
        acoes_link[i].click()
        time.sleep(5)
        obter_dados_da_empresa(driver)
        driver.back()
        time.sleep(3)
        i = i + 2
        if i < qtde_acoes:
            scroll_height = scroll_height + 40            
        else:
            break
    print("Dados das empresa obtidos")
    
def obter_dados_da_empresa(driver):
    iframe = driver.find_element_by_tag_name("iframe")
    driver.switch_to.frame(iframe)
    tabela_acao = driver.find_element_by_tag_name("table")
    linhas_tabela_acao = tabela_acao.find_elements_by_tag_name("tr")

    nome_empresa = linhas_tabela_acao[0].find_elements_by_tag_name("td")[1].text
    atividades = linhas_tabela_acao[3].find_elements_by_tag_name("td")[1].text
    classificacao_setorial = linhas_tabela_acao[4].find_elements_by_tag_name("td")[1].text
    print(nome_empresa)
    print(atividades)
    print(classificacao_setorial)

    driver.find_element_by_xpath("/html/body/div[2]/div[1]/ul/li[1]/div/table/tbody/tr[2]/td[2]/a[1]").click()
    links_codigos_acoes = tabela_acao.find_elements_by_class_name("LinkCodNeg")
    for acao in links_codigos_acoes:
        print(acao.text)

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
            time.sleep(3)
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


#This example requires Selenium WebDriver 3.13 or newer
with webdriver.Firefox() as driver:
    set_atividades_empresa = {}
    set_classificacao_setorial_empresa = {}
    set_dados_empresa = {}
    wait = WebDriverWait(driver, 10)    
    driver.get("http://www.b3.com.br/pt_br/produtos-e-servicos/negociacao/renda-variavel/empresas-listadas.htm")    
    driver.maximize_window()
    time.sleep(5)
    acessar_dados_das_empresas(driver)
    
    
    

###############################################################################################

# from selenium import webdriver
# from selenium.webdriver.common.by import By
# from selenium.webdriver.common.keys import Keys
# from selenium.webdriver.support.ui import WebDriverWait
# from selenium.webdriver.support.expected_conditions import presence_of_element_located
# import time
# 
# #This example requires Selenium WebDriver 3.13 or newer
# with webdriver.Firefox() as driver:
#     set_atividades_empresa = {}
#     set_classificacao_setorial_empresa = {}
#     set_dados_empresa = {}
#     wait = WebDriverWait(driver, 10)    
#     driver.get("http://www.b3.com.br/pt_br/produtos-e-servicos/negociacao/renda-variavel/empresas-listadas.htm")    
#     time.sleep(5)
#     iframe = driver.find_element_by_tag_name("iframe")
#     driver.switch_to.frame(iframe)
#     letras_link = driver.find_elements_by_class_name("letra")    
#     letras_link[0].click()
#     time.sleep(5)
#     
#     tabela_acoes = driver.find_element_by_tag_name("table")
#     acoes_link = tabela_acoes.find_elements_by_tag_name("a")
#     acoes_link[0].click()
#     time.sleep(5)
#     
#     iframe = driver.find_element_by_tag_name("iframe")
#     driver.switch_to.frame(iframe)
#     tabela_acao = driver.find_element_by_tag_name("table")
#     linhas_tabela_acao = tabela_acao.find_elements_by_tag_name("tr")
# 
#     nome_empresa = linhas_tabela_acao[0].find_elements_by_tag_name("td")[1].text
#     atividades = linhas_tabela_acao[3].find_elements_by_tag_name("td")[1].text
#     classificacao_setorial = linhas_tabela_acao[4].find_elements_by_tag_name("td")[1].text
#     print(nome_empresa)
#     print(atividades)
#     print(classificacao_setorial)
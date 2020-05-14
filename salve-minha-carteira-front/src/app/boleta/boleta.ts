export class BoletaAgrupadoPeloCodigoNegociacao {
    codigoNegociacaoPapel: string
    somatorias: Somatoria[]

    constructor(codigoNegociacaoPapel: string, somatorias: Somatoria[]) {
        this.codigoNegociacaoPapel = codigoNegociacaoPapel;
        this.somatorias = somatorias;
    }

    rentabilidadeComprasVendas(): number {
        return this.valorTotalVendas() - (this.valorMedioCompras() * this.quantidadeTotalVendas())
    }

    percentualRentabilidadeComprasVendas(): number {
        return this.rentabilidadeComprasVendas() / (this.valorMedioCompras() * this.quantidadeTotalVendas());
    }

    valorMedioCompras(): number {
        return this.valorTotalCompras() / this.quantidadeTotalCompras();
    }

    valorMedioVendas(): number {
        return this.valorTotalVendas() / this.quantidadeTotalVendas();
    }

    valorTotal(): number {
        return this.valorTotalCompras() - this.valorTotalVendas();
    }

    quantidadeTotal(): number {
        return this.quantidadeTotalCompras() - this.quantidadeTotalVendas()
    }

    valorTotalCompras(): number {
        let s = this.somatorias.find(s => s.tipo == "COMPRA")
        return s == null ? 0.00 : s.valorTotal
    }

    quantidadeTotalCompras(): number {
        let s = this.somatorias.find(s => s.tipo == "COMPRA")
        return s == null ? 0.00 : s.quantidadeTotal
    }

    valorTotalVendas(): number {
        let s = this.somatorias.find(s => s.tipo == "VENDA")
        return s == null ? 0.00 : s.valorTotal
    }

    quantidadeTotalVendas(): number {
        let s = this.somatorias.find(s => s.tipo == "VENDA")
        return s == null ? 0.00 : s.quantidadeTotal        
    }
}

export class Somatoria {
    tipo: string
    valorTotal: number
    quantidadeTotal: number

    constructor(valorTotal: number, quantidadeTotal: number) {
        this.valorTotal = valorTotal;
        this.quantidadeTotal = quantidadeTotal;
    }
}
export class BoletaAgrupadoPeloCodigoNegociacao {
    id: number
    codigoNegociacaoPapel: string
    quantidadeTotal: number

    constructor(id :number, codigoNegociacaoPapel: string, quantidadeTotal: number) {
        this.id = id;
        this.codigoNegociacaoPapel = codigoNegociacaoPapel;
        this.quantidadeTotal = quantidadeTotal;
    }
}
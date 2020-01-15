export class BoletaAgrupadoPeloCodigoNegociacao {
    codigoNegociacaoPapel: string
    quantidadeTotal: number

    constructor(codigoNegociacaoPapel: string, quantidadeTotal: number) {
        this.codigoNegociacaoPapel = codigoNegociacaoPapel;
        this.quantidadeTotal = quantidadeTotal;
    }
}
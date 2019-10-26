export class Token {
    hash: string
    dataGeracao: Date

    constructor(hash: string) {
        this.hash = hash
        this.dataGeracao = new Date();
    }
}
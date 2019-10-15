import { HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

export class Errors {
    static mapearErro(err :any) :Observable<never> {
        if (err instanceof HttpErrorResponse) {
            switch(err.status) {
                case 400:
                    return throwError(new ArgumentosInvalidosError(err.error.message));
                case 409:
                    return throwError(new JaExisteError(err.error));
            }
        }
        return throwError(new SalveMinhaCarteiraError())
    }
}

export class SalveMinhaCarteiraError implements Error {
    name: string;
    message: string;
    stack?: string;

    constructor(message? :string) {
        this.message = message == null ? "Não foi possível processar sua requisição, tente novamente mais tarde" : message;
        this.name = this.getName();
    }

    getName() :string {
        return "SalveMinhaCarteiraError";
    }
}

export class JaExisteError extends SalveMinhaCarteiraError {
    constructor(message :string) {
        super(message == null ? "Já cadastrado" : message);
    }

    getName() :string {
        return "BadRequestError";
    }
}

export class ArgumentosInvalidosError extends SalveMinhaCarteiraError {
    constructor(message :string) {
        super(message == null ? "Já cadastrado" : message);
    }

    getName() :string {
        return "BadRequestError";
    }
}
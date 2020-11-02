export interface landingPageInterface {
    history: any
}

export interface formDataInterface {
    data: Object,
    nickname: string,
    password: string,
    email?: string,
    name?: string,
}

export interface createAccountDataInterface {
    nickname: string,
    password: string,
    email: string,
    name: string,
}

export interface accountFormInterface {
    type: String,
    history: any
}

export interface appInterface {
    history: any
}

export interface renderAccountDataInterface {
    [index: number]: inputDataInterface,
    map: any,
}

export interface inputDataInterface {
    name: string,
    type: string,
    placeholder: string,
    ref: any,
    errors?: ErrorTypeInterface
}

export interface ErrorTypeInterface {
    type?: string,
    message?: string
}

export interface checkErrorTypeInterface {
    type?: string,
}
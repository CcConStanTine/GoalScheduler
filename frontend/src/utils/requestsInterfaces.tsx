interface FailedResponse {
    timestamp: string;
    status: number;
    error: string;
    path: string;
    message: string;
};

export enum RequestsMethods {
    GET = 'get',
    POST = 'post',
    DELETE = 'delete',
    PATCH = 'patch',
};

export interface UserSignUp {
    message: string;
};

export interface UserSignIn extends FailedResponse {
    token: string;
    type: string;
    id: number;
    username: string;
    email: string;
    roles: string[];
};

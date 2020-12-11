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

export interface UserSignIn extends FailedResponse {
    token: string;
    type: string;
    id: number;
    username: string;
    email: string;
    roles: string[];
};

export interface UserSignUp extends FailedResponse {
    message: string;
};

export interface GetUserInfo extends FailedResponse {
    userId: number;
    firstName: string;
    lastName: string;
    nick: string;
    email: string;
}

export interface GetUserPhoto extends FailedResponse {
    imageId: null,
    fileUrl: string;
}

export interface PostUserPhoto extends FailedResponse {
    imageId: number,
    fileUrl: string;
}

export interface ReturnToken {
    token: string;
}

export interface Plans extends FailedResponse {
    content?: string;
    endDate?: string;
    fulfilled?: boolean;
    importance?: string;
    startDate?: string;
    urgency?: string;
    yearPlanId?: number;
    monthPlanId?: number;
    dayPlanId?: number;
}

export interface ReturnPlans extends FailedResponse {
    [key: number]: Plans;
    map: any;
}

export interface ReturnTypeDataById extends FailedResponse {
    dayId?: number;
    dayName?: string;
    dayDate?: string;
    monthId?: number;
    monthName?: string;
    daysAmount?: number;
    yearId?: number;
    yearNumber?: number;
    leapYear?: boolean;
};

export interface AuthConfig {
    headers: {
        Authorization: string;
    }
}

export interface UserValues {
    token: string;
    id: number;
    username: string;
}

export interface DeleteOrChange extends FailedResponse {
    message: string;
}



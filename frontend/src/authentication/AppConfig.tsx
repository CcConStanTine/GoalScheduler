class AppConfig {
    protected readonly serverAddress = 'https://goalscheduler.herokuapp.com';
    private readonly axiosType = "Bearer";
    protected token: string;
    protected userId: number;
    protected username: string;

    constructor() {
        this.token = null!;
        this.userId = null!;
        this.username = null!;
    }

    protected getAuthConfig = () => ({
        headers: {
            Authorization: `${this.axiosType} ${this.token}`,
        }
    });
}

export default AppConfig;
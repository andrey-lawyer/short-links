import axios from "axios";

export class ApiService {
    static path = process.env.REACT_APP_BACKEND_PATH;

    static async createShortLink(oldLink) {
        const { data } = await axios.post(`${this.path}/l/shorten`, oldLink);
        return data;
    }

    static async getStatistics(shortUrl, page = 0, size = 10, deviceType, operatingSystem) {
        const params = { page, size };

        if (deviceType) {
            params.deviceType = deviceType;
        }

        if (operatingSystem) {
            params.operatingSystem = operatingSystem;
        }

        const { data } = await axios.get(`${this.path}/l/stats/${shortUrl}`, {
            params,
        });

        return data;
    }
}
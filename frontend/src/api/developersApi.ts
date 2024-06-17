import axios, {AxiosResponse} from 'axios';
import {Developer} from "../types/developer.ts";
import {DeveloperResponse} from "../types/developer.ts";
import {createDevelopersArray} from "../common/utils.ts";

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchAllDevelopers = async (): Promise<DeveloperResponse[]> => {
    const endpoint = `${API_BASE_URL}/developers`;
    try {
        const response: AxiosResponse<Developer[], DeveloperResponse> = await axios.get(endpoint);
        return createDevelopersArray(response);
    } catch
        (error) {
        console.error('Error fetching all developers:', error);
        throw error;
    }
}
export const fetchDevelopersByLanguage = async (language: string): Promise<DeveloperResponse[]> => {

    try {
        const response: AxiosResponse<Developer[], DeveloperResponse> = await axios.get(`${API_BASE_URL}/developers/${language}`);
        return createDevelopersArray(response);
    } catch (error) {
        console.error(`Error fetching developers by language (${language}):`, error);
        throw error;
    }
};
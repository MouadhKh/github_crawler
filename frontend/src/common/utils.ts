import {AxiosResponse} from "axios";
import {Developer, DeveloperResponse} from "../types/developer.ts";

export function createDevelopersArray(response: AxiosResponse<Developer[], DeveloperResponse>) {
    return response.data.map((developer: Developer) => {
        const developerRes: DeveloperResponse =
            {
                username: developer.username,
                languages: developer.languages,
                numberOfRepositories: developer.repositories.length

            }
        return developerRes;
    });
}
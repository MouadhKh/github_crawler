export interface Developer {
    username: string;
    repositories: string[];
    languages: string[];
}
// Rendering repository list would make api response unreadable at this level
export interface DeveloperResponse {
    username: string;
    numberOfRepositories: number;
    languages: string[];
}
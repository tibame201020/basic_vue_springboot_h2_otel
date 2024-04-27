export interface RequestRecord {
    recordId: number;
    requestPath: string;
    userEmail: string;
    traceId: string;
    recordDateTime: string;
    isError: boolean;
    responseStatus: number;
}
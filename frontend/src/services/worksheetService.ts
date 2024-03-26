import {IRes} from "../types/resType";
import {apiService} from "./apiService";
import {IWorksheet} from "../interfaces/IWorksheetResponse";

const worksheetService = {
    getWorksheets(): IRes<IWorksheet[]> {
        return apiService.get("worksheets")
    },
    getWorksheetById(id:number):IRes<IWorksheet> {
        return apiService.get(`worksheets/${id}`)
    },
    getAdminContent(): IRes<any> {
        return apiService.get("admin")
    },
    createWorksheet(worksheet: IWorksheet):IRes<any> {
        return apiService.post("worksheets", worksheet);
    }
}
export {
    worksheetService
}
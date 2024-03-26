import React, {useEffect, useState} from "react";
import {IWorksheet} from "../../interfaces/IWorksheetResponse";
import {worksheetService} from "../../services/worksheetService";
import {Worksheet} from "./Worksheet";

const Worksheets = () => {
    const [worksheets, setWorksheets] = useState<IWorksheet[]>([]);
    useEffect(() => {
        worksheetService.getWorksheets().then(({data}) => {
            console.log(data);
            setWorksheets(data);
        })
    }, [])
    return (
        <div>
            <h1>Worksheets: </h1>
            {worksheets.map(worksheet => <Worksheet key={worksheet.worksheetId} worksheet={worksheet}/>)}
        </div>
    );
};

export {Worksheets};
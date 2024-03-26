import React, {useEffect, useState} from 'react';
import {worksheetService} from "../services/worksheetService";
import {IWorksheet} from "../interfaces/IWorksheetResponse";
import {Worksheets} from "../components/Worksheets/Worksheets";

const WorksheetsPage = () => {

    return (
        <div>
            <Worksheets/>
        </div>
    );
};

export {WorksheetsPage};
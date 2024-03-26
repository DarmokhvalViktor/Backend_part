import React, {FC, PropsWithChildren} from "react";
import {IWorksheet} from "../../interfaces/IWorksheetResponse";
import {useNavigate} from "react-router-dom";

interface IProps extends PropsWithChildren {
    worksheet: IWorksheet
}
const Worksheet:FC<IProps> = ({worksheet}) => {

    const navigate = useNavigate();
    const toWorksheet = () => {
        navigate('/worksheetInfo', {state: {worksheetId:worksheet.worksheetId}})
    }

    return (
        <div>
            <div onClick={toWorksheet}>
                <h1>{worksheet.title}</h1>
            </div>
        </div>
    );
};

export {Worksheet};
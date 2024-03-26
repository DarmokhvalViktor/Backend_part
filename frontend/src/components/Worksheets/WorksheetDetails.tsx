import {useLocation} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {IWorksheet} from "../../interfaces/IWorksheetResponse";
import {worksheetService} from "../../services/worksheetService";

const WorksheetDetails = () => {
    const location = useLocation();
    const worksheetId:number = location.state.worksheetId

    const [worksheet, setWorksheet] = useState<IWorksheet>();
    useEffect(() => {
        worksheetService.getWorksheetById(worksheetId).then(({data}) => {
            console.log(data);
            setWorksheet(data);
        })
    }, [worksheetId])

    return (
        <div>
            {worksheet ? (
                <div key={worksheet.worksheetId}>
                    <h2>Worksheet ID: {worksheet.worksheetId}</h2>
                    <div>Class Year: {worksheet.classYear}</div>
                    <div>Instruction: {worksheet.instruction}</div>
                    <div>Subject: {worksheet.subject}</div>
                    <div>Title: {worksheet.title}</div>
                    <h3>Sentences: </h3>
                    {worksheet.sentences.map(sentence => (
                        <div key={sentence.sentenceId}>
                            <p>{sentence.content}</p>
                            <h4>Answers: </h4>
                            <ul>
                                {sentence.answers.map(answer => (
                                    <li key={answer.answerId}>
                                        {answer.answerContent} {answer.isCorrect ? '(Correct)' : ''}
                                    </li>
                                ))}
                            </ul>
                        </div>
                    ))}
                </div>
            ) : (<div>Loading....</div>)}
        </div>
    );
};

export {WorksheetDetails};
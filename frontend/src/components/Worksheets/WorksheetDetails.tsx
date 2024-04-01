import {useLocation, useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {IWorksheet} from "../../interfaces/IWorksheetResponse";
import {worksheetService} from "../../services/worksheetService";

const WorksheetDetails = () => {
    const location = useLocation();
    const worksheetId:number = location.state.worksheetId
    const navigate = useNavigate();


    const [worksheet, setWorksheet] = useState<IWorksheet>();
    useEffect(() => {
        worksheetService.getWorksheetById(worksheetId).then(({data}) => {
            console.log(data);
            setWorksheet(data);
        })
    }, [worksheetId])


    const handleDelete = async () => {
        const deletedWorksheet = await worksheetService.deleteWorksheet(worksheet.worksheetId);
        console.log(deletedWorksheet);
        navigate("/worksheets");
    };
    const handleUpdate = async () => {
        navigate(`/createOrUpdateWorksheet/${worksheetId}`); // Navigate to update page with worksheet ID
    };

    const getQuestionTypeLabel = (questionType: string) => {
        switch (questionType) {
            case "FILL_BLANK":
                return "Fill the blank with your answer";
            case "ONE_ANSWER":
                return "Choose one right answer";
            case "MULTIPLE_ANSWERS":
                return "Choose multiple right answers";
            default:
                return "";
        }
    };

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
                            <p>QuestionType: {getQuestionTypeLabel(sentence.questionType)}</p>
                            <p>Question: {sentence.content}</p>
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
                    <button onClick={handleDelete}>Delete</button>
                    <button onClick={handleUpdate}>Update</button>
                </div>
            ) : (<div>Loading....</div>)}
        </div>
    );
};

export {WorksheetDetails};
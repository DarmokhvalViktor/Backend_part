import {useLocation, useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {IWorksheet} from "../../interfaces/IWorksheetResponse";
import {worksheetService} from "../../services/worksheetService";
import "./WorksheetDetails.css"

const WorksheetDetails = () => {
    const location = useLocation();
    const worksheetId:number = location.state.worksheetId
    const navigate = useNavigate();
    const [showResults, setShowResult] = useState(false);
    const [currentQuestion, setCurrentQuestion] = useState(0);
    const [score, setScore] = useState(0);


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

    const answerClicked = (isCorrect: boolean) => {
        if (isCorrect) {
            setScore(score + 1);
        }
        if (currentQuestion + 1 < worksheet.sentences.length) {
            setCurrentQuestion(currentQuestion + 1);
        } else {
            setShowResult(true);
        }
    }
    const restart = () => {
        setScore(0);
        setCurrentQuestion(0);
        setShowResult(false);
    }

    return (
        <div className="App">
            {
                worksheet ? (
                    <div>
                        <h1>{worksheet.title}</h1>
                        <h3>{worksheet.subject}</h3>
                        <h4>{worksheet.instruction}</h4>
                        <h2>Score: {score}</h2>

                        {
                            showResults ? (
                                <div className="final-results">
                                    <h1>Final results</h1>
                                    <h2>{score} out of {worksheet.sentences.length} correct - (
                                        {(score / worksheet.sentences.length) * 100} %)
                                    </h2>
                                    <button onClick={() => restart()}>Restart quiz</button>
                                </div>
                            ) : (
                                <div className="question-card">
                                    <h2>Question: {currentQuestion + 1} out of {worksheet.sentences.length}</h2>
                                    <h3>{worksheet.sentences[currentQuestion].content}</h3>
                                    <h3 className="question-text">{worksheet.sentences[currentQuestion].questionType}</h3>
                                    <ul>
                                        {worksheet.sentences[currentQuestion].answers.map((answer) => {
                                            return (
                                                <li key={answer.answerId}
                                                    onClick={() => answerClicked(answer.isCorrect)}>
                                                    {answer.answerContent}
                                                </li>
                                            );
                                        })}
                                    </ul>
                                </div>
                            )
                        }
                        <button onClick={handleDelete}>Delete</button>
                        <button onClick={handleUpdate}>Update</button>
                    </div>
                ) : (
                    <div>Loading.....</div>
                )}
        </div>
    )

    // const getQuestionTypeLabel = (questionType: string) => {
    //     switch (questionType) {
    //         case "FILL_BLANK":
    //             return "Fill the blank with your answer";
    //         case "ONE_ANSWER":
    //             return "Choose one right answer";
    //         case "MULTIPLE_ANSWERS":
    //             return "Choose multiple right answers";
    //         default:
    //             return "";
    //     }
    // };



    // return (
    //     <div>
    //         {worksheet ? (
    //             <div key={worksheet.worksheetId}>
    //                 <h2>Worksheet ID: {worksheet.worksheetId}</h2>
    //                 <div>Class Year: {worksheet.classYear}</div>
    //                 <div>Instruction: {worksheet.instruction}</div>
    //                 <div>Subject: {worksheet.subject}</div>
    //                 <div>Title: {worksheet.title}</div>
    //                 <h3>Sentences: </h3>
    //                 {worksheet.sentences.map(sentence => (
    //                     <div key={sentence.sentenceId}>
    //                         <p>QuestionType: {getQuestionTypeLabel(sentence.questionType)}</p>
    //                         <p>Question: {sentence.content}</p>
    //                         <h4>Answers: </h4>
    //                         <ul>
    //                             {sentence.answers.map(answer => (
    //                                 <li key={answer.answerId}>
    //                                     {answer.answerContent} {answer.isCorrect ? '(Correct)' : ''}
    //                                 </li>
    //                             ))}
    //                         </ul>
    //                     </div>
    //                 ))}
    //                 <button onClick={handleDelete}>Delete</button>
    //                 <button onClick={handleUpdate}>Update</button>
    //             </div>
    //         ) : (<div>Loading....</div>)}
    //     </div>
    // );
};

export {WorksheetDetails};
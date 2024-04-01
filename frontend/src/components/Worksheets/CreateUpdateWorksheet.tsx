import { useForm } from "react-hook-form";
import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import { worksheetService } from "../../services/worksheetService";
import { IWorksheet, Sentence, Answer } from "../../interfaces/IWorksheetResponse";

const CreateUpdateWorksheet = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const isAddMode = !id;

    const { register, handleSubmit, setValue } = useForm<IWorksheet>();
    const [sentences, setSentences] = useState<Sentence[]>([{ content: '', answers: [{ answerContent: '', isCorrect: false }], questionType: '' }]);
    const [worksheet, setWorksheet] = useState<IWorksheet | null>(null);

    useEffect(() => {
        if (!isAddMode) {
            // Fetch worksheet data if in update mode
            worksheetService.getWorksheetById(parseInt(id))
                .then(({data}) => {
                    setWorksheet(data);
                    const { title, classYear, instruction, subject, sentences: worksheetSentences } = data;
                    setValue('title', title);
                    setValue('classYear', classYear);
                    setValue('instruction', instruction);
                    setValue('subject', subject);
                    setSentences(worksheetSentences);
                })
                .catch(error => console.error('Error fetching worksheet:', error));
        }
    }, [id, isAddMode, setValue]);

    const addSentence = () => {
        setSentences([...sentences, { content: '', answers: [{ answerContent: '', isCorrect: false }], questionType: '' }]);
    };

    const addAnswer = (sentenceIndex: number) => {
        const updatedSentences = [...sentences];
        updatedSentences[sentenceIndex].answers.push({ answerContent: '', isCorrect: false });
        setSentences(updatedSentences);
    };

    const toggleCorrectAnswer = (sentenceIndex: number, answerIndex: number) => {
        const updatedSentences = [...sentences];
        updatedSentences[sentenceIndex].answers[answerIndex].isCorrect = !updatedSentences[sentenceIndex].answers[answerIndex].isCorrect;
        setSentences(updatedSentences);
    };

    const deleteSentence = (sentenceIndex: number) => {
        const updatedSentences = sentences.filter((_, index) => index !== sentenceIndex);
        setSentences(updatedSentences);
    };

    const deleteAnswer = (sentenceIndex: number, answerIndex: number) => {
        const updatedSentences = [...sentences];
        updatedSentences[sentenceIndex].answers = updatedSentences[sentenceIndex].answers.filter((_, index) => index !== answerIndex);
        setSentences(updatedSentences);
    };

    const onSubmit = async (data: IWorksheet) => {
        try {
            data.sentences = sentences;
            console.log(data);
            if (isAddMode) {
                await worksheetService.createWorksheet(data);
                navigate('/worksheets');
            } else {
                await worksheetService.updateWorksheet(data, parseInt(id));
                navigate('/worksheets');
            }
        } catch (error) {
            console.error('Error:', error);
            // Handle error
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div>
                <label>Title:</label>
                <input type="text" {...register('title', { required: true })} />
            </div>
            <div>
                <label>Class Year:</label>
                <input type="text" {...register('classYear', { required: true })} />
            </div>
            <div>
                <label>Instruction:</label>
                <input type="text" {...register('instruction', { required: true })} />
            </div>
            <div>
                <label>Subject:</label>
                <input type="text" {...register('subject', { required: true })} />
            </div>

            {/* Dynamically add sentences */}
            {sentences.map((sentence, sentenceIndex) => (
                <div key={sentenceIndex}>
                    <label>Sentence {sentenceIndex + 1}:</label>
                    <input
                        type="text"
                        value={sentence.content}
                        onChange={(e) => setSentences(prev => {
                            const updatedSentences = [...prev];
                            updatedSentences[sentenceIndex].content = e.target.value;
                            return updatedSentences;
                        })}
                    />
                    <select
                        value={sentence.questionType}
                        onChange={(e) => {
                            const selectedQuestionType = e.target.value;
                            setSentences((prevSentences) => {
                                const updatedSentences = [...prevSentences];
                                updatedSentences[sentenceIndex].questionType = selectedQuestionType;
                                return updatedSentences;
                            });
                        }}
                    >
                        <option value="">Select Question Type</option>
                        <option value="FILL_BLANK">FILL_BLANK</option>
                        <option value="ONE_ANSWER">ONE_ANSWER</option>
                        <option value="MULTIPLE_ANSWERS">MULTIPLE_ANSWERS</option>
                    </select>
                    <button type="button" onClick={() => deleteSentence(sentenceIndex)}>Delete Sentence</button>
                    <button type="button" onClick={() => addAnswer(sentenceIndex)}>Add Answer</button>
                    {/* Dynamically add answers */}
                    {sentence.answers.map((answer, answerIndex) => (
                        <div key={answerIndex}>
                            <label>
                                <input
                                    type="checkbox"
                                    checked={answer.isCorrect}
                                    onChange={() => toggleCorrectAnswer(sentenceIndex, answerIndex)}
                                />
                                Correct Answer:
                            </label>
                            <input
                                type="text"
                                value={answer.answerContent}
                                onChange={(e) => setSentences(prev => {
                                    const updatedSentences = [...prev];
                                    updatedSentences[sentenceIndex].answers[answerIndex].answerContent = e.target.value;
                                    return updatedSentences;
                                })}
                            />
                            <button type="button" onClick={() => deleteAnswer(sentenceIndex, answerIndex)}>Delete Answer</button>
                        </div>
                    ))}
                </div>
            ))}

            <button type="button" onClick={addSentence}>Add Sentence</button>
            <button type="submit">{isAddMode ? 'Create Worksheet' : 'Update Worksheet'}</button>
        </form>
    );
};

export { CreateUpdateWorksheet };
import {Sentence, IWorksheet} from "../../interfaces/IWorksheetResponse";
import {useForm} from "react-hook-form";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {worksheetService} from "../../services/worksheetService";

const CreateWorksheet = () => {
    const { register, handleSubmit } = useForm<IWorksheet>();
    const [sentences, setSentences] = useState<Sentence[]>([{ content: '', answers: [{ answerContent: '', isCorrect: false }] }]);
    const navigate = useNavigate();

    const addSentence = () => {
        setSentences([...sentences, { content: '', answers: [{ answerContent: '', isCorrect: false }] }]);
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
            await worksheetService.createWorksheet(data);
            navigate("/worksheets");
        } catch (error) {
            console.error('Error creating worksheet:', error);
            // Handle error
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
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
            <div>
                <label>Title:</label>
                <input type="text" {...register('title', { required: true })} />
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
            <button type="submit">Create Worksheet</button>
        </form>
    );
};

export {CreateWorksheet};
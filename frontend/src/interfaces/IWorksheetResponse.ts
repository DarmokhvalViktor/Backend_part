export interface Answer {
  answerId?: number;
  answerContent: string;
  isCorrect: boolean | null;
  sentenceId?: number;
}

export interface Sentence {
  answers: Answer[];
  content: string;
  questionType: string;
  worksheetId?: number;
  sentenceId?: number;
}

export interface IWorksheet {
  classYear: string;
  instruction: string;
  sentences: Sentence[];
  subject: string;
  title: string;
  worksheetId?: number;
}
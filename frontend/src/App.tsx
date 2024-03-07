import React, {useEffect, useState} from 'react';
import axios from "axios";

interface Worksheet {
    worksheetId: number;
    title: string;
    classYear: number;
    instruction: string;
    subject: string;
    sentences: Sentence[];
}
interface Sentence {
    sentenceId: number;
}

const App = () => {
    const[worksheets, setWorksheets] = useState<Worksheet[]>([]);
    useEffect(() => {
        axios.get<Worksheet[]>("api/tests").then(({data}) => setWorksheets(data));
    }, [])

  return (
      <div>
          <h1>ATTENTION!!</h1>
          {
              worksheets.map(worksheet => <div key={worksheet.worksheetId}>
                  <div>title: {worksheet.title}</div>
                  <div>classYear: {worksheet.classYear}</div>
                  <div>instruction: {worksheet.instruction}</div>
                  <div>subject: {worksheet.subject}</div>
                  <div>sentences: {worksheet.sentences.map(sentence => <div key={sentence.sentenceId}>
                      <div>sentenceId: {sentence.sentenceId}</div>
                  </div>)}</div>
              </div>)
          }
      </div>
  );
};

export {App};
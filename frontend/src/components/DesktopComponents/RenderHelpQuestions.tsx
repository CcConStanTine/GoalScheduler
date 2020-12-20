import React from 'react';
import { QuestionContent, renderQues } from '../../utils/interfaces';

const renderHelpQuestions = (userQuestions: renderQues) => userQuestions.map(({ title, answer }: QuestionContent) =>
    <details className='question-container entry-container' key={title}>
        <summary>{title}<p></p></summary>
        <span>{answer}</span>
    </details>
);

export default renderHelpQuestions;
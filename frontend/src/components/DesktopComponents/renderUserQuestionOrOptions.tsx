import React from 'react';
import { QuestionContent, RenderQues } from '../../utils/interfaces';

const renderUserQuestionOrOptions = (userQuestions: RenderQues) => userQuestions.map(({ title, answer }: QuestionContent) =>
    <details className='question-container entry-container' key={title}>
        <summary>{title}<p></p></summary>
        <span>{answer}</span>
    </details>
);

export default renderUserQuestionOrOptions;
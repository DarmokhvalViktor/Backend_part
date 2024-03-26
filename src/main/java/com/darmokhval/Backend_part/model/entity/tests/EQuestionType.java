package com.darmokhval.Backend_part.model.entity.tests;

public enum EQuestionType {
    ONE_ANSWER("ONE_ANSWER"),
    MULTIPLE_ANSWERS("MULTIPLE_ANSWERS"),
    FILL_BLANK("FILL_BLANK");
    private final String questionType;
    EQuestionType(String questionType) {
        this.questionType = questionType;
    }
    public String getQuestionType() {
        return questionType;
    }
}

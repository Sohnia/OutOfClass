package com.graduate.outofclass.bean;

public class QuestionBean {

    public QuestionBean() {
    }

    /**
     * 对应的就是Filter1-7  还有一个选中答案
     */

    //编号
    private int id;
    //问题
    private String question;
    //四个选项
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    //答案
    private int answer;
    //详情
    private String explainAction;
    //题目类型，是否是选择题
    private boolean isChoice;
    //题目章节
    private int section;
    //用户输入的答案
    private String inputAnswer;
    //用户选中的答案
    private int selectedAnswer;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getExplainAction() {
        return explainAction;
    }

    public void setExplainAction(String explainAction) {
        this.explainAction = explainAction;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getInputAnswer() {
        return inputAnswer;
    }

    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", answer='" + answer + '\'' +
                ", explainAction='" + explainAction + '\'' +
                ", isChoice=" + isChoice +
                ", section=" + section +
                ", inputAnswer='" + inputAnswer + '\'' +
                ", selectedAnswer=" + selectedAnswer +
                '}';
    }
}

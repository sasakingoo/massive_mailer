package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.javalite.activejdbc.validation.ValidationException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

@RunWith(TestWithDB.class)
public class QuestionTest {

    @Test
    public void shouldReturnAListOfIds() {
        Question.createIt("description", "desc1", "advice", "adv1", "is_multi_question", 0);

        List<Object> allIds = Question.getNRandomIds(1).collect(Collectors.toList());

        assertThat(allIds, is(not(empty())));
        assertThat(allIds.size(), is(1));
    }

    @Test
    public void shouldReturnEmptyListIfNoQuestionsArePresent() {
        List<Object> allIds = Question.getNRandomIds(5).collect(Collectors.toList());
        assertThat(allIds, is(empty()));
    }

    @Test
    public void shouldGetQuestionById() {
        Question question1 = Question.createIt("description", "desc1", "advice", "adv1", "category", "scrum");
        Long id = question1.getLongId();
        Question actual = Question.getById(id);
        assertThat(actual, is(equalTo(question1)));
    }

    @Test
    public void getCorrectOption_正解のIDの一覧を返す() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        AnswerOption correct1 = AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        AnswerOption correct2 = AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);

        final List<Long> expected = new ArrayList<>();
        expected.add(correct1.getLongId());
        expected.add(correct2.getLongId());

        final ArrayList<Long> actual = question.getCorrectOption();
        assertEquals(actual, expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExxxceptionIfInvalidId() {
        Question question1 = Question.createIt("description", "desc1", "advice", "adv1");
        Long id = question1.getLongId();

        Question.getById(id + 10);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAllowEmptyDescription() {
        Question.createIt("description", null, "advice", "adv1");

    }

    @Test
    public void shouldAllowEmptyAdvice() {
        Question question1 = Question.createIt("description", "desc1", "advice", null);
        assertThat(question1.getLongId(), is(not(nullValue())));
        assertThat(question1.getAdvice(), isEmptyString());
    }

    @Test
    public void shouldFetchOptionsForQuestion() {
        Question question = Question.createIt("description", "desc1", "advice", null);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        assertThat(question.getOptions(), is(not(empty())));
    }

    @Test
    public void shouldFetchOptionsForQuestionWithSameQuestionId() {
        Question question = Question.createIt("description", "desc1", "advice", null);
        Long expectedQuestionId = question.getLongId();
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);

        question.getOptions().forEach(option -> assertThat(option.getQuestionId(), is(equalTo(expectedQuestionId))));
    }

    @Test
    public void shouldIsMultipleChoiceQuestion() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        assertTrue(question.isMultipleAnswerOptions());
    }

    @Test
    public void shouldIsSingleChoiceQuestion() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        assertFalse(question.isMultipleAnswerOptions());
    }

    @Test
    public void Questionテーブルのカラムが全て返ってくる() {
        Category cat = Category.createIt("name", "xxx");
        Question.createIt("description", "desc1", "advice", "adv1", "category", cat.getLongId(), "is_multi_question", 0);
        List<Question> actual = Question.getNRandom(1);
        assertThat(actual.get(0).getDescription(), is(equalTo("desc1")));
        assertThat(actual.get(0).getAdvice(), is(equalTo("adv1")));
        assertThat(actual.get(0).getCategoryName(), is(equalTo("xxx")));
        assertFalse(actual.get(0).getIsMultiQuestion());
    }

    @Test
    public void TypeがsingleのときにgetIsMultiQuestionが0を返す() {
        final Question question = new Question("description", "advice", "category", "single");
        final boolean actual = question.getIsMultiQuestion();
        assertEquals(actual, false);
    }

    @Test
    public void TypeがMultiのときにgetIsMultiQuestionが1を返す() {
        final Question question = new Question("description", "advice", "category", "multiple");
        final boolean actual = question.getIsMultiQuestion();
        assertEquals(actual, true);
    }

    @Test
    public void Questionテーブルから指定したカテゴリのquestionが返す() {
        Category cat = Category.createIt("name", "cat");
        Category dog = Category.createIt("name", "dog");
        Question.createIt("description", "desc1", "advice", "adv1", "category", cat.getLongId(), "is_multi_question", 0);
        Question.createIt("description", "desc2", "advice", "adv2", "category", dog.getLongId(), "is_multi_question", 0);

        List<Question> actual = Question.getNRandomWhereCategory(1, dog.getLongId().toString());
        assertThat(actual.get(0).getDescription(), is(equalTo("desc2")));
        assertThat(actual.get(0).getAdvice(), is(equalTo("adv2")));
        assertThat(actual.get(0).getCategoryName(), is(equalTo("dog")));
        assertFalse(actual.get(0).getIsMultiQuestion());
    }

    @Test
    public void カテゴリごとに任意の数のquestionを返す() {
        Category cat = Category.createIt("name", "cat");
        Category dog = Category.createIt("name", "dog");
        Category bird = Category.createIt("name", "bird");
        Question.createIt("description", "desc", "advice", "adv", "category", cat.getLongId(), "is_multi_question", 0);
        Question.createIt("description", "desc", "advice", "adv", "category", cat.getLongId(), "is_multi_question", 0);
        Question.createIt("description", "desc", "advice", "adv", "category", cat.getLongId(), "is_multi_question", 0);
        Question.createIt("description", "desc", "advice", "adv", "category", dog.getLongId(), "is_multi_question", 0);
        Question.createIt("description", "desc", "advice", "adv", "category", dog.getLongId(), "is_multi_question", 0);
        Question.createIt("description", "desc", "advice", "adv", "category", bird.getLongId(), "is_multi_question", 0);

        Map<String, Integer> categoryMap = new HashMap<>();
        categoryMap.put(cat.getLongId().toString(), 2);
        categoryMap.put(dog.getLongId().toString(), 1);

        List<Question> questions = Question.getNRandomByCategories(categoryMap);

        Map<String, List<Question>> result = questions.stream().collect(Collectors.groupingBy(Question::getCategoryName));
        assertEquals(3, questions.size());
        assertEquals(2, result.get("cat").size());
    }

    @Ignore
    @Test
    public void shouldReturnRightAnswer() {
        Question question = new Question("description", "advice", "category", "multiple");
        String[] optionIds = new String[2];
        boolean actual = question.verifyAnswer(Arrays.asList(optionIds));
        assertEquals(actual,true);
    }

    @Test
    public void shouldReturnEmptyListByGetAll() {
        List<Question> questions = Question.getAll();

        assertEquals(questions.size(), 0);
    }

    @Test
    public void shouldReturnTwoElementByGetAll() {
        Question.createIt("description", "desc", "advice", "adv", "category", "team", "is_multi_question", 0);
        Question.createIt("description", "desc", "advice", "adv", "category", "team", "is_multi_question", 0);
        List<Question> questions = Question.getAll();
        assertEquals(questions.size(), 2);
    }

    @Test
    public void isPublicがtrueを返す() {
        final Question question = new Question("description", "advice", "category", "multiple");
        final boolean actual = question.isPublic();
        assertEquals(actual, true);
    }
}

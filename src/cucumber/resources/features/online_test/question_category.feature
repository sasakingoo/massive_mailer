Feature:
  問題表示の前にカテゴリー選択が表示される

  @developing
  Scenario: カテゴリーのチェックボックスが全て外れる
    Given カテゴリー選択画面が表示される
    And クリアボタンが表示される
    And カテゴリーのチェックボックスにチェックが入っている
    When クリアボタンをクリック
    Then カテゴリーのチェックボックスのチェックが全て外れる

  @developing
  Scenario: カテゴリーのチェックボックスを全部外してスタートボタンが押せない
    Given カテゴリー選択画面が表示される
    And カテゴリーのチェックボックスにチェックが入っていない
    When スタートボタンをクリック
    Then Question画面に遷移しない

  @developing
  Scenario: Questionがないカテゴリーはチェックボックスに表示されない
    Given カテゴリー選択画面が表示される
    And "scrum"カテゴリーにQuestionが存在しない
    Then チェックボックスに"scrum"カテゴリーが表示されない

  @developing
  Scenario: Questionがあるカテゴリーをチェックボックスに表示する
    Given カテゴリー選択画面が表示される
    And "scrum"カテゴリーのQuestionが存在している
    Then チェックボックスに"scrum"カテゴリーが表示される

  Scenario: スタートボタンを押したら、Question画面に遷移する
    Given カテゴリー選択画面が表示される
    And 問題が存在している
    And カテゴリーのチェックボックスにチェックが入っている
    When スタートボタンをクリック
    Then Question画面へ遷移する
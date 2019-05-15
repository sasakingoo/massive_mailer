Feature:
  ユーザは忘却曲線の再出題頻度に合わせた問題が見れる

  @developing
  Scenario: 未回答の質問が特訓の質問に出てくる
    Given 2問の質問がある
    And 初回の特訓で1問正解して、1問不正解する
    When 1日後に特訓開始をする
    Then 初回特訓ときに不正解した質問のみが表示されること

  @developing
  Scenario Outline: n連続で正解している質問が、最終回答日からx日後に表示される
    Given "<連続正解回数>"連続で正解している1つの質問がある
    When 前回の質問回答日から"<回答がまだできない経過日数>"日以内に特訓を実施する
    Then 質問が表示されない
    When 前回の質問回答日から"<回答が可能になる経過日数>"日後に特訓を実施する
    Then 質問が表示される
    Examples:
      | 連続正解回数 | 回答がまだできない経過日数 | 回答が可能になる経過日数 |
      | 1          | 1                       | 2                    |
      | 2          | 6                       | 7                    |
      | 3          | 19                      | 20                   |
      | 4          | 59                      | 60                   |

  @developing
  Scenario: 5連続正解している問題はもう表示しない
    Given 5連続で正解している1つの質問がある
    When 前回の質問回答日から100日後に特訓を実施する
    Then 質問が表示されない
# DDSJava

桥牌双明牌玩法求解库Java接口。

## 桥牌双明牌玩法介绍

双明牌玩法即在正常的桥牌规则之外，令所有玩家在一开始就明牌的一种玩法。

## 运行时环境

- Java version 1.8 32-bit.
- JNA(Java Native Access 5.0.0)
- Windows or Debian/Ubuntu system.

## 开发环境

- Intellij idea 2018 with JDK 1.8 32-bit.
- JNA(Java Native Access 5.0.0)

## 基本介绍

### DDS

DDS（Double-Dummy Solver <https://github.com/dds-bridge/dds>）是开源的桥牌计算库，不仅能单线程计算，还能利用多线程提高效率。DDS的接口是典型的C语言式的，由结构、指针和基本数据类型来传递参数。在较新的版本中，DDS使用PBN（Portable Bridge Notation)来记录桥牌牌局状态。

### PBN

PBN（Portable Bridge Notation <https://www.tistis.nl/pbn/>）是记录桥牌游戏的通用标记法。所有的计算机程序都可以采用PBN来表示和分析桥牌牌局。PBN的一个例子是“E:AT5.AJT.A632.KJ7 Q763.KQ9.KQJ94.T 942.87653..98653 KJ8.42.T875.AQ42”，在这个例子中，开头的E表示起始Player的位是East（其他如S,W,N），之后的每一组“AT5.AJT.A632.KJ7”表示延顺时针方向排列的每一个Player手上的牌，由“."分隔不同的花色，大写字母或数字表示牌名的缩写。

### JNA

JNA（Java Native Access）提供一组Java工具类用于在运行期间动态访问系统本地库，它实际上是JNI的封装，开发者只需要在一个Java接口中描述目标原生库的函数与结构，就可以由JNA自动实现Java接口到Native Library的映射。

## API接口

### JNA接口

```Java
public interface DDSImport extends Library {
    DDSImport instance = (DDSImport) Native.load("dds", DDSImport.class);

    public int CalcDDtablePBN(DDTableDealPBN.ByValue tableDealPBN, DDTableResults.ByReference tableResults);

    public int SolveBoardPBN(DDDealPBN.ByValue dealPBN, int target, int solutions, int mode, DDFutureTricks.ByReference futureTricks, int threadIndex);
}
```

- CalcDDtablePBN可计算共20种初始订约，每个牌手5种（4个花色+无将）。
- SolveBoardPBN可计算下一步出牌。

### JNA Structure

提供了一组类来模拟DDS中的C语言结构。

### class DDSConnect

DDSConnect封装了以上的JNA接口，使得开发者可通过面向对象的方式操作数据。

```Java
public FutureTricks solveBoardPBN(BridgeGame game, int target, int solutions, int mode) throws DDSException
public BestCard solveBoardPBNBestCard(BridgeGame game) throws DDSException
public FutureTricks solveBoard(BridgeGame game) throws DDSException
public List<Contract> calcMakableContracts(String pbn) throws DDSException
```

### package bridge.domain

bridge.domain提供了组成桥牌游戏基本部件的类（BridgeGame, Card, CardColor, Contract, Deck, PlayerPosition, Rank, Suit, Trick, Trump），并提供一个子包（utils），这些类的设计均考虑到了对于DDS的兼容性。

#### class Bridge

BridgeGame提供了对于牌局的基本操作，包括获得当前墩、获得当前牌桌状态、获得所有已经完成的墩、获得庄家、获得明手、获得剩余牌数、出牌并）取下一个玩家的位置、获取南北和东西的墩数等方法。

```Java
Trick getCurrentTrick() // 返回当前墩（可以用来判断上一回合是否已经结束）。
Dictionary<PlayerPosition, Deck> getGameState() // 返回当前牌桌状态，其中每一个PlayerPosition都有一个Deck。
List<Trick> getTricks() // 获取已经完成的所有的墩（时间顺序）。
PlayerPosition getDeclarer() // 获取庄家。
PlayerPosition getDummy() // 获取明手。
Contract getContract() // 获取定约。
BridgeGame(Dictionary<PlayerPosition, Deck> gameState, String contractShortStr) // 构造函数，gameState是牌桌状态，contractShortStr的格式同Contract.getShortString()。
int getCardsRemaining() // 获得牌桌上剩余的牌的数量。
PlayerPosition playerCard(Card card, PlayerPosition playerPosition) // 出一张牌，参数card是牌，playerPosition是出牌人的位置，返回下一个出牌人的位置。
int getNorthSouthTricksMade() // 获得南北得到的墩数。
int getEastWestTricksMade() // 获得东西得到的墩数。
```

#### class Card

Card描述了一张牌的基本信息，并实现了Comparable接口。

```Java
PlayerPosition getPlayerPosition() // 获得这张牌的所有者（出派人）的位置。
void setPlayerPosition(PlayerPosition playerPosition) // 设置这张牌的所有者（出派人）的位置。
void getRand() // 获得这张牌的分值。
void getSuit() // 获得这张牌的花色。
Card(Rank rank, Suit suit) // 构造函数，两个参数分别是这张牌的分值和花色。
Card(int rankScore, Suit suit) // 构造函数，rankScore将被用来构造一个新的Rank。
CardColor getColor() //获得这张牌的颜色，CardColor是枚举类，其值为BLACK或RED。
```

重载了compareTo、equals、toString、hashCode。

#### class Contract

Contract描述了定约。

```Java
int getValue() // 获得定约的值。
void setValue(int value) // 设置定约的值。
Trump getTrump() // 获得将牌。
void setTrump(Trump trump) //设置将牌。
PlayerPosition getPlayerPosition() // 获得庄家的位置。
void setPlayerPosition() // 设置庄家的位置。
Contract() // 无参构造函数。
Contract(String contractShortStr) // 构造函数，参数contractShortStr的格式见下。
String getShortString() // 返回定约的内容（简单形式），格式为“playerPosition.getFirstLetter() + ":" + Integer.toString(value) + trump.getShortName()”。
```

重载了toString。

#### class Deck

Deck描述了一个牌堆

```Java
List<Card> getCards() // 获得牌堆中所有的牌。
void setCards() // 设置牌堆中所有的牌。
Card getTopCard() // 获得最顶端的牌。
Card getBottomCard() // 获得最底端的牌。
Card getCardWithHighestRank() // 获得分值最大的牌。
boolean notEmpty() // 判断牌堆是否为非空。
void removeCard(Card card) // 从牌堆里移走指定的牌。
void addCard(Card card) // 向牌堆里加入指定的牌。
Card getCard(Rank rank, Suit suit) // 获得牌堆里具有指定分值和花色的牌。
Card getCard(int score, Suit suit) // 同上。
boolean has(Rank rank, Suit suit) // 判断牌堆里是否具有指定分值和花色的牌。
boolean has(int score, Suit suit) // 同上。
int getCount() // 获得牌堆里牌的总数。
```

重载了toString。

#### class PlayerPosition

PlayerPosition描述了一个玩家的位置，该类只有四种实例。

| order | pbnIndex | fullName | firstLetter |
| ------ | ------ | ------ | ------|
| 0 | 1 | North | N |
| 1 | 2 | East | E |
| 2 | 3 | South | S |
| 3 | 0 | West | W |

各种get与set方法和构造函数不再赘述，重载了equals、toString和hashCode。

#### class Rank

Rank描述了所有合法的牌的分值，该类只有十三种实例。

| score | shortName | fullName |
| ------ | ------ | ------ |
| 2 | 2 | Two |
| 3 | 3 | Three |
| 4 | 4 | Four |
| 5 | 5 | Five |
| 6 | 6 | Six |
| 7 | 7 | Seven |
| 8 | 8 | Eight |
| 9 | 9 | Nine |
| 10 | T | Ten |
| 11 | J | Jack |
| 12 | Q | Queen |
| 13 | K | King |
| 14 | A | Ace |

各种get与set方法和构造函数不再赘述，重载了equals、toString和hashCode。

#### class Suit

Suit 描述了所有合法的花色，该类只有五个实例。

| order | shortName | fullName |
| ------ | ------ | ------ |
| 0 | S | Spades |
| 1 | H | Hearts |
| 2 | D | Diamonds |
| 3 | C | Clubs |
| 4 | N | NoTrump |

各种get与set方法和构造函数不再赘述，重载了equals、toString和hashCode。

#### class Trick

Trick描述了一回合中的出牌。

```Java
PlayerPosition getTrickDealer() // 获得第一个出牌的人的位置。
void setTrickDealer(PlayerPosition trickDealer) // 设置第一个出牌的人的位置。
Deck getDeck() // 获得这一回合中出过的牌。
void setDeck(Deck deck) // 设置这一回合中出过的牌。
PlayerPosition getTrickWinner() // 获得本回合赢家。
void setTrickWinner(PlayerPosition trickWinner) // 设置本回合赢家。
Suit getTrickDealerSuit() // 获得第一个出牌的人所出的牌的花色。
Trick() // 无参构造函数。
```

### class Trump

Trump描述了将牌的信息，其只有五种实例，同Suit。

### package utils

utils提供类一些工具类。

#### class BridgeHelper

BridgeHelper包括了一些常用的静态函数。

```Java
PlayerPosition getNextPlayerPosition(PlayerPosition currentSide) // 返回currentSide的下家。
BridgeGame getGameFromPBN(String pbnHand, String contract) // 根据PBNHand和定约（shortString)获得一个BridgeGame的实例。
String toPBN(BridgeGame game) // 将game转换为PBNHand。
String DeckToPBNPlay(Deck deck) // 将deck转换为PBNPlay。
String DeckToPBNHand(Deck deck) // 将Deck转换为PBNHand。
Enumeration<Card> readPBNCard(Suit suit, String cardString) // 读取PBNCard。
Deck getDeck(String pbnHand) // 根据pbnHand，获得牌堆。
Card getCard(String card) // 读取单个卡牌。
```

### class DDSException

DDSExpection提供了一个异常类用于解读DDS所返回的错误代码

| Error Code | Explanation |
| ------ | ------ |
| -1 | Unknown fault |
| -2 | Zero cards |
| -3 | target > Number of tricks left |
| -4 | Duplicated cards |
| -5 | target < -1 |
| -7 | target > 13 |
| -8 | solutions < 1 |
| -9 | solutions > 3 |
| -10 | No of cards > 52 |
| -11 | Not used |
| -12 | Suit or rank value out of range for deal.currentTrickSuit or deal.currentTrickRank |
| -13 | Card already played in the current trick is also defined as a remaining card to play |
| -14 | Wrong number of remaining cards for a hand |
| -15 | threadIndex < 0 or >= noOfThreads, noOfThreads is the configured maximum number of threads |
| -16 | mode is less than 0 |
| -17 | mode is greater than 2 |
| -18 | trump is not one of 0, 1, 2, 3, 4 |
| -19 | first is not one of 0, 1, 2 |
| -98 | AnalysePlay*() family of functions. (a) Less than 0 or more than 52 cards supplied.  (b) Invalid suit or rank supplied. (c) A played card is not held by the right player. |
| -99 | Faulty PBN string |
| -101 | Too many threads |
| -102 | Failed to create thread |
| -103 | Failed to wait for all threads to complete |
| -201 | No suit |
| -202 | Too many tables |
| -301 | Chunk size is less than 1 |

## Sample

### Source Code

```Java
package ddsjava.sample;

import bridge.domain.BridgeGame;
import bridge.domain.Contract;
import bridge.domain.PlayerPosition;
import bridge.domain.utils.BridgeHelper;
import ddsjava.DDSException;
import ddsjava.DDSConnect;
import ddsjava.dto.BestCard;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        DDSConnect dds = new DDSConnect();
        String pbnCode = "E:AT5.AJT.A632.KJ7 Q763.KQ9.KQJ94.T 942.87653..98653 KJ8.42.T875.AQ42";
        System.out.println("Board: " + pbnCode);
        List<Contract> contracts = null;
        try {
             contracts = dds.calcMakableContracts(pbnCode);
        } catch (DDSException e) {
            e.printStackTrace();
            System.out.println("Error Code: " + e.getErrorCode());
            System.exit(1);
        }
        System.out.println("Best Results:");
        for (Contract contract : contracts) {
            System.out.println(contract);
        }
        for (Contract contract : contracts) {
            System.out.println("------------- Game Starts ----------------");
            System.out.println("Contract: " + contract);
            BridgeGame game = BridgeHelper.getGameFromPBN(pbnCode, contract.getShortString());
            System.out.println("Trump: " + game.getContract().getTrump());
            PlayerPosition player = BridgeHelper.getNextPlayerPosition(game.getDeclarer());
            while (game.getCardsRemaining() > 0) {
                BestCard result = null;
                try {
                    result = dds.solveBoardPBNBestCard(game);
                } catch (DDSException e) {
                    System.out.println("Error Message: " + e.getMessage());
                    e.printStackTrace();
                    System.exit(1);
                }
                System.out.println(player + ": " + result.getCard() + ". Score: " + result.getScore());
                player = game.playCard(result.getCard(), player);
                if (game.getCurrentTrick().getDeck().getCount() == 0) {
                    System.out.println("Trick Winner: " + game.getTricks().get(game.getTricks().size() - 1).getTrickWinner());
                }
            }
            System.out.println("-----------Results----------");
            System.out.println("South/North: " + game.getNorthSouthTricksMade() + " tricks");
            System.out.println("East/West: " + game.getEastWestTricksMade() + " tricks");
        }
    }
}
```

### Output

```code
Board: E:AT5.AJT.A632.KJ7 Q763.KQ9.KQJ94.T 942.87653..98653 KJ8.42.T875.AQ42
Best Results:
North:8♠
East:5♠
South:8♠
West:5♠
North:5♥
East:8♥
South:4♥
West:8♥
North:10♦
East:3♦
South:10♦
West:3♦
North:5♣
East:8♣
South:5♣
West:8♣
North:9N
East:4N
South:9N
West:4N
------------- Game Starts ----------------
Contract: North:8♠
Trump: ♠
East: A♥. Score: 5
South: 9♥. Score: 8
West: 3♥. Score: 5
North: 2♥. Score: 8
Trick Winner: East
East: A♦. Score: 4
South: 4♦. Score: 8
West: 3♣. Score: 4
North: 5♦. Score: 8
Trick Winner: East
East: 6♦. Score: 3
South: K♦. Score: 8
West: 2♠. Score: 3
North: 8♦. Score: 8
Trick Winner: West
West: 6♣. Score: 2
North: A♣. Score: 8
East: 7♣. Score: 2
South: T♣. Score: -2
Trick Winner: North
North: 4♥. Score: 7
East: J♥. Score: -2
South: K♥. Score: -2
West: 8♥. Score: -2
Trick Winner: South
South: Q♥. Score: 6
West: 7♥. Score: -2
North: 4♣. Score: 6
East: T♥. Score: -2
Trick Winner: South
South: 3♠. Score: 5
West: 9♠. Score: 2
North: J♠. Score: 5
East: A♠. Score: 2
Trick Winner: East
East: 3♦. Score: 1
South: Q♦. Score: 5
West: 4♠. Score: 1
North: 7♦. Score: 5
Trick Winner: West
West: 9♣. Score: 0
North: 2♣. Score: 5
East: J♣. Score: 0
South: 7♠. Score: 5
Trick Winner: South
South: 9♦. Score: 4
West: 8♣. Score: 0
North: T♦. Score: -2
East: 2♦. Score: -2
Trick Winner: North
North: Q♣. Score: 3
East: K♣. Score: -2
South: 6♠. Score: 3
West: 5♣. Score: -2
Trick Winner: South
South: Q♠. Score: 2
West: 6♥. Score: -2
North: 8♠. Score: 2
East: 5♠. Score: 0
Trick Winner: South
South: J♦. Score: 1
West: 5♥. Score: 0
North: K♠. Score: 1
East: T♠. Score: 0
Trick Winner: North
-----------Results----------
South/North: 8 tricks
East/West: 5 tricks
......
```
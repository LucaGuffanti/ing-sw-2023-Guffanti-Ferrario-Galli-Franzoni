package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Sack;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.utils.CsvToBoardParser;
import it.polimi.ingsw.server.model.utils.CsvToShelfParser;
import jdk.jfr.Label;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements methods and contains attributes useful for
 * printing game objects and messages.
 */
public class Printer {

    // RESET
    public static final String RESET = "\033[0m";

    // NORMAL COLORS
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // BOLD
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    // UNDERLINED
    public static final String BLACK_UNDERLINED = "\033[4;30m";
    public static final String RED_UNDERLINED = "\033[4;31m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";

    // BACKGROUNDS
    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";

    // INTENSITY
    public static final String BLACK_BRIGHT = "\033[0;90m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String YELLOW_BRIGHT = "\033[0;93m";
    public static final String BLUE_BRIGHT = "\033[0;94m";
    public static final String PURPLE_BRIGHT = "\033[0;95m";
    public static final String CYAN_BRIGHT = "\033[0;96m";
    public static final String WHITE_BRIGHT = "\033[0;97m";

    // HIGH INTENSITY
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";

    // HIGH INTENSITY BACKGROUNDS
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[48;5;118m";
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[48;5;25m";
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";
    public static final String LIGHT_BROWN_BACKGROUND_BRIGHT = "\033[48;5;137m";
    public static final String DARK_BROWN_BACKGROUND_BRIGHT = "\033[48;5;94m";

    // MY SHELFIE
    public static final String nameOfGameART = " " + "                                                                                                                                                    \n" +
            "                                               @P!?@#                                       .^#:@                   \n" +
            " 7&&@@@7      P@@@@#                          &@P ~B&#&   &?JJ!                   :PG5    !B&####G7                 \n" +
            "  !P&@#.      ^&@@#:                         5@@~ :P&@@&   ^G&@#7                    G@5 :G@P:G:@@@5                \n" +
            "   5&&&~      J@&@P                          5&@P   &#@     ~B&G.                    G&Y P@G  @##&@#                \n" +
            "   5#&@&.    ~&@&@5         .^!.    !!!~^     B@@B^         ~B&G.                    P&Y B@?   B##B^                \n" +
            "   5#&P&G   .#&G#@5     ^P#&&@G.   :G@@P^      #@@@B7       ~B&G.  .                 P&Y P@?                        \n" +
            "   Y#&^Y&J  P@7J#@5      .^5&@B.  :P@&J         Y#&@@&Y.    ~B&#PG##BPJ:             P&J Y&B       &@#              \n" +
            "   YB&~ P&~?&5 Y#@5         7B@#^^G@&7             G&&&&5.  ~G&&@&GP#&&B:   .!JYY7:  5&J :G@?     !GP     ~?YY?^    \n" +
            "   JB&7 .G&&G. 5B&Y          ~P&&#&#~               :P&&&B. ~G&&P:   P&&~ :5B?:..!#  5&J 5B&&#GBJ 7?J~  ?BY^  ^GB.  \n" +
            "   JG&J  !#&~  PB&Y           !5&&G:        :7?7^     G&&#J ~G&#.    J##::P&B   ~P&5 &?.  !&&^    B&&! ##J    J&5   \n" +
            "   JG&Y .P##5  PB&J  ^JPGBP:  7G&P.        YB###BP:   ?&#BP ~P&G.    Y#5 7##?!!!!!7~ J#?    5&P   5#B: #5!!!!!77    \n" +
            "   ?P&P   ... .PB&J 7P#B##&? !G&J.        .####GPPY  .G##P? ~P#P    .P#7 ?B#^      Y J#?    Y#B.  5#B: #J           \n" +
            "  .JG##^      7PB&5 !Y#B5555G##7           ?###PPP?7YB#B5?. !P#P.   !P#5 :G#P^    ## ?B7   .5#B.  5BB: #B!.    &5   \n" +
            " :Y5GGGG^    7PGGGG5 !YBBBB#BP~             :7YPGGBBBGY?~  :PGGGJ  ^GGGG? .?PGGGGP  @5G5   JPPP!  5PP~ ~5GGGBGP?.   \n";

    // GAME OBJECTS RELATED STUFF
    public static final Map<ObjectTypeEnum, String> objectTypeToRender = new HashMap<>();
    public static final Map<String, ObjectTypeEnum[][]> personalIdToRender = new HashMap<>();
    public static final Map<String, String> commonIdToRender = new HashMap<>();

    public static final Map<String, String> jsonCardIdToResourceCardId = new HashMap<>();

    public static final String cg1 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"             ┌─────┬─────┐             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"             │  =  │  =  │             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"             ├─────┼─────┤             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"             │  =  │  =  │             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"             └─────┴─────┘             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                  x2                   "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";

    public static final String cg2 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ┌─────┐                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  ≠  │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  ≠  │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  ≠  │     x2          "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  ≠  │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  ≠  │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  ≠  │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               └─────┘                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg3 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         ┌ ─ ─ ─ ─ ─ ─ ─ ─ ┐           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     ┌─────┐     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     │  =  │     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     ├─────┤     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     │  =  │     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     ├─────┤     |  x4       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     │  =  │     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     ├─────┤     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     │  =  │     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         |     └─────┘     |           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         └ ─ ─ ─ ─ ─ ─ ─ ─ ┘           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg4 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"           ┌ ─ ─ ─ ─ ─ ─ ─ ─ ┐         "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"           |     ┌─────┐     |         "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"           |     │  =  │     |         "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"           |     ├─────┤     |         "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"           |     │  =  │     |         "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"           |     └─────┘     |         "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"           └ ─ ─ ─ ─ ─ ─ ─ ─ ┘         "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                    x6                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg5 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ┌─────┐                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │     │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │     │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │     │   MAX  3≠       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤     x3          "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │     │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │     │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               ├─────┤                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │     │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               └─────┘                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg6 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ┌─────┬─────┬─────┬─────┬─────┐     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │  ≠  │  ≠  │  ≠  │  ≠  │  ≠  │     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   └─────┴─────┴─────┴─────┴─────┘     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                  x2                   "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg7 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ┌─────┬─────┬─────┬─────┬─────┐     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │     │     │     │     │     │     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   └─────┴─────┴─────┴─────┴─────┘     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               MAX  3≠                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                 x4                    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg8 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ┌─────┐── ── ────── ── ──┌─────┐    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │  =  │                  │  =  │    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   └─────┘                  └─────┘    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   |                              |    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ┌─────┐                  ┌─────┐    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │  =  │                  │  =  │    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   └─────┘── ── ────── ── ──└─────┘    "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg9 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         ┌─────┐     ┌─────┐           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         │  =  │     │  =  │           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         └─────┘     └─────┘           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"    ┌─────┐    ┌─────┐    ┌─────┐      "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"    │  =  │    │  =  │    │  =  │      "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"    └─────┘    └─────┘    └─────┘      "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"    ┌─────┐    ┌─────┐    ┌─────┐      "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"    │  =  │    │  =  │    │  =  │      "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"    └─────┘    └─────┘    └─────┘      "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg10 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         ┌─────┐     ┌─────┐           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         │  =  │     │  =  │           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         └─────┼─────┼─────┘           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  =  │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         ┌─────┼─────┼─────┐           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         │  =  │     │  =  │           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         └─────┘     └─────┘           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";

    public static final String cg11 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ┌─────┐                             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │  =  │                             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   └─────┼─────┐                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         │  =  │                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"         └─────┼─────┐                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               │  =  │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"               └─────┼─────┐           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                     │  =  │           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                     └─────┼─────┐     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                           │  =  │     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                           └─────┘     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";
    public static final String cg12 = "" +
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ┌─────┐                             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │     │                             "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ├─────┼─────┐                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │     │     │                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" COMMON "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ├─────┼─────┼─────┐                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"   **   "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │     │     │     │                 "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  GOAL  "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ├─────┼─────┼─────┼─────┐           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │     │     │     │     │           "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   ├─────┼─────┼─────┼─────┼─────┐     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   │     │     │     │     │     │     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"   └─────┴─────┴─────┴─────┴─────┘     "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n"+
            WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"                                       "+RESET+LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"        "+RESET+"\n";






    static {
        objectTypeToRender.put(ObjectTypeEnum.CAT, GREEN_BACKGROUND_BRIGHT+" "+RESET);
        objectTypeToRender.put(ObjectTypeEnum.TROPHY, CYAN_BACKGROUND_BRIGHT+" "+RESET);
        objectTypeToRender.put(ObjectTypeEnum.TOY, YELLOW_BACKGROUND_BRIGHT+" "+RESET);
        objectTypeToRender.put(ObjectTypeEnum.BOOK, WHITE_BACKGROUND_BRIGHT+" "+RESET);
        objectTypeToRender.put(ObjectTypeEnum.FRAME, BLUE_BACKGROUND_BRIGHT+" "+RESET);
        objectTypeToRender.put(ObjectTypeEnum.PLANT, PURPLE_BACKGROUND_BRIGHT+" "+RESET);

        try {
            personalIdToRender.put("1", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/1.csv"));
            personalIdToRender.put("2", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/2.csv"));
            personalIdToRender.put("3", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/3.csv"));
            personalIdToRender.put("4", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/4.csv"));
            personalIdToRender.put("5", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/5.csv"));
            personalIdToRender.put("6", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/6.csv"));
            personalIdToRender.put("7", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/7.csv"));
            personalIdToRender.put("8", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/8.csv"));
            personalIdToRender.put("9", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/9.csv"));
            personalIdToRender.put("10", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/10.csv"));
            personalIdToRender.put("11", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/11.csv"));
            personalIdToRender.put("12", CsvToShelfParser.simpleConvert("src/main/assets/cards/personalGoalCSV/12.csv"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        jsonCardIdToResourceCardId.put("0", "4");
        jsonCardIdToResourceCardId.put("1", "11");
        jsonCardIdToResourceCardId.put("2", "8");
        jsonCardIdToResourceCardId.put("3", "7");
        jsonCardIdToResourceCardId.put("4", "3");
        jsonCardIdToResourceCardId.put("5", "2");
        jsonCardIdToResourceCardId.put("6", "1");
        jsonCardIdToResourceCardId.put("7", "6");
        jsonCardIdToResourceCardId.put("8", "5");
        jsonCardIdToResourceCardId.put("9", "10");
        jsonCardIdToResourceCardId.put("10", "9");
        jsonCardIdToResourceCardId.put("11", "12");

        commonIdToRender.put("1", cg1);
        commonIdToRender.put("2", cg2);
        commonIdToRender.put("3", cg3);
        commonIdToRender.put("4", cg4);
        commonIdToRender.put("5", cg5);
        commonIdToRender.put("6", cg6);
        commonIdToRender.put("7", cg7);
        commonIdToRender.put("8", cg8);
        commonIdToRender.put("9", cg9);
        commonIdToRender.put("10", cg10);
        commonIdToRender.put("11", cg11);
        commonIdToRender.put("12", cg12);
    }

    public static void log(String s) {
        System.out.println(s);
    }

    public static void error(String s) {
        System.out.println(RED_BOLD+s+RESET);
    }

    public static void printShelf(ObjectTypeEnum[][] shelf) {
        int wantedWidth = 3;
        for (int x = 0; x < shelf[0].length; x++) {
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"     "+RESET);
        }
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+" "+RESET);
        System.out.print("\n");
        for (int y = 0; y < shelf.length; y++) {
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" "+y+" "+RESET);

            for (int x = 0; x < shelf[0].length; x++) {
                System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+" "+RESET);
                for (int rep = 0; rep < wantedWidth; rep++) {
                    if (shelf[y][x] == null) {
                        System.out.print(DARK_BROWN_BACKGROUND_BRIGHT+" "+RESET);
                    } else {
                        System.out.print(objectTypeToRender.get(shelf[y][x]));
                    }
                }
            }
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"   "+RESET);
            System.out.print("\n");
        }
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"   "+RESET);
        for (int x = 0; x < shelf[0].length; x++) {
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  "+x+" "+RESET);
        }
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"   "+RESET);
        System.out.print("\n");
    }

    public static void printBoard(ObjectTypeEnum[][] board) {
        int wantedWidth = 3;
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"   "+RESET);
        for (int x = 0; x < board[0].length; x++) {
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  "+x+" "+RESET);
        }
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"   "+RESET);
        System.out.print("\n");
        for (int y = 0; y < board.length; y++) {
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" "+y+" "+RESET);

            for (int x = 0; x < board[0].length; x++) {
                System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+" "+RESET);
                for (int rep = 0; rep < wantedWidth; rep++) {
                    if (board[y][x] == null) {
                        System.out.print(DARK_BROWN_BACKGROUND_BRIGHT+" "+RESET);
                    } else {
                        System.out.print(objectTypeToRender.get(board[y][x]));
                    }
                }
            }
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+" "+y+" "+RESET);
            System.out.print("\n");
        }
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"   "+RESET);
        for (int x = 0; x < board[0].length; x++) {
            System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"  "+x+" "+RESET);
        }
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+"   "+RESET);
        System.out.print("\n");
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"COORDINATE SYSTEM:          "+RESET+"\n");
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"╔══x      With y increasing "+RESET+"\n");
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"║y        going through the "+RESET+"\n");
        System.out.print(LIGHT_BROWN_BACKGROUND_BRIGHT+BLACK_BOLD+"          rows              "+RESET+"\n");
    }
    public static void printCommonGoalCard(String id) {
        System.out.println(commonIdToRender.get(jsonCardIdToResourceCardId.get(id)));
    }

    public static void printPersonalGoalCard(String id) {
        printShelf(personalIdToRender.get(id));
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+" x 1 "+RESET);
        for (int x = 2; x < 7; x++) {
            System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"  "+x+" "+RESET);
        }
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+" "+RESET);
        System.out.print("\n");
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+" * 1 "+RESET);
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"  2 "+RESET);
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"  4 "+RESET);
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"  6 "+RESET);
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+"  9 "+RESET);
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+" 12 "+RESET);
        System.out.print(WHITE_BACKGROUND_BRIGHT+BLACK_BOLD+" "+RESET);
        System.out.print("\n");
    }

    public static void printName() {
        System.out.println(nameOfGameART);
    }

    @Label("DEBUG")
    public static void main(String[] args) throws Exception {
        String s = "src/test/resources/shelfTEST/endGameShelfCheck_full.csv";
        Printer.printShelf(GameObjectConverter.simplifyShelfIntoMatrix(CsvToShelfParser.convert(s)));
        System.out.print("\n");
        s = "src/test/resources/shelfTEST/diagonalValid.csv";
        Printer.printShelf(GameObjectConverter.simplifyShelfIntoMatrix(CsvToShelfParser.convert(s)));
        s = "src/test/resources/boardTEST/expectedCellObjectCard_4players.csv";

        Sack sack = new Sack();
        Board b = new Board(4, sack);
        Printer.printBoard(GameObjectConverter.simplifyBoardIntoMatrix(b));

        for (Integer i = 1; i <= 12; i++) {
            System.out.println("ID: "+i.toString());
            printPersonalGoalCard(i.toString());
        }

        for (Integer i = 0; i < 12; i++) {
            System.out.println("ID: "+i.toString());
            printCommonGoalCard(i.toString());
        }
        printName();
    }
}

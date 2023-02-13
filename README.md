# Watson1dot0
An assistant, to play 'Dad's Infamous Word Guessing Game' - Version 1.0

## In the beginning...
I've always been a bit of a wordsmith.  Growing up, I had a strong vocabulary and spelling skills, and I always enjoyed crafty, eloquent humor and saw (and still see) a strong use of spoken language as a way to convey thoughtfulness, and intellect.  

This aptitude stayed with me over the years.  Letters and words for whatever reason, just come naturally to me and 'leap off the page' - so much so most of my family members don't dare to play me in 'Scrabble' or any other word based game, as I tend to be deadly at them.  

A few years ago, I was introduced to 'Dad's Infamous Word Guessing Game'.  It really does not have a name, as apparently he and Mom played it either before I born, or when I was very young.  So - what is this game?  It plays a little like 'Wordle' - only I maintain that this 'Wordle' variant is harder.  

The basic gameplay is like this:
Your opponent will  choose a familiar 5 letter word.  See if you can guess the word!  Each time you make a guess, your opponent will respond with a number.  The number represents the number of letters in your guess that appear in the word chosen by your opponent.  
(Example:  If the opponent chooses 'PUPPY' and you guess 'LOOPY' the response would be 2 ('P' makes 1, and 'Y' makes 2.)
With this knowledge - can you determine your opponents word, more quickly than they can determine a word you have selected which they will try to guess.

I was first introduced to this game a few years ago (right around the time I decided to go 'all in' on the pursuit of a career change to software development.  IMMEDIATELY after being introduced to this game, my very first though was:  "I bet I can write a computer program that can play this game better than I can!"

## Introducing Watson1dot0:  The first kick at the can...
Having completed introductory level studies into Java, I felt I now had enough familiarization with key concepts that would allow me to accomplish the goal - to write a program that could play this game better than I could.  And thus Watson1dot0 was launched.  Why Watson?  This is an homage to the IBM AI computer of the same name, that once took on (and beat) a pair of Jeopardy 'Super Champions' - Brad Rutter and Ken Jennings (oh, yeah - I'm also a Jeopardy nut.  RIP Alex Trebek)

## Watson1dot0:  What it can (and can't do)...
My word game play hobby and growing interest in logical and critical thinking (in pursuit of software development) posit certain assumptions when setting out to write this program:
'Wheel of Fortune' suggests that most players target 'R, S, T, L, N, E' as the most desirable letter to play when solving their puzzles
Students of 'Scrabble' suggest that any of the letters in the ~word~ 'STARLINE' are desirable to retain, as they are among the most frequently used, and are therefore a 'good leave' to have on your rack.  
In general, it would seem that while playing 'Dad's Infamous Word Guessing Game' that if subsequent guesses were to vary by only 1 letter at a time, this would put the player in the best position to determine as much as possible about whether or not a letter appears in the opponents word.  

Having only introductory level exposure and skills to Java when I wrote this, while I seemed to possess enough fundamental knowledge to accomplish the goal, the result when executed is quite limited.  The assistant does a respectable job of traking letters that are know to be in the opponents word, or not in, or, are undetermined - but this version of the assistant CAN'T SPELL.  It can determine which 5 letters are in the opponents word - but it couldn't make a word out of those 5 letters.  It also can't offer strategy.  The user needs to devise and implement a strategy.  

This said, with the knowledge I possessed at the time, this 'first kick at the can' is 'feature complete' and does a 'reasonable job' of meeting the original objective of 'playing this game better than I could'.  It can arrive at the 5 letters in your opponents word, and do so fairly quickly (offering basic strategy suggestions along the way) - but as skills grew, I saw the potential for something better - a version that can SPELL, offer situational strategies, and LEARN.  Standby for Watson2dot0

## Why is this years in the making?
- I have a full time job (in the technology field - but not in software development (yet)
- I have 2 young kids at home (under the age of 14)
- I have twice the number of dogs (we LOVE Siberean Huskies)
- I am studying for a new career (in software development) at the same time

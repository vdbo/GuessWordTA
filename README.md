# The small project documentation

### What was chosen?
The challenge #1 about falling words

### How much time was invested (within the given limit)
I spent around 8 hours doing [current implementation](https://github.com/vdbo/GuessWordTA/commit/534f0a097c00f7ce72fb9d7013610d7b7a734ee5)

### How was the time distributed (concept, model layer, view(s), game mechanics)
- Concept = 1h
- Base setup and architecture = 30m
- View related code = 3h
- Game mechanics code and other = 3h 30m
- Documentation (out of time) = 45m

### Decisions made to solve certain aspects of the game
- Game mechanics:
  - User needs to choose whether falling word matches with original or not
  - There are sets of 3 possible matches to one original word and 1 pair is always correct, e.g "holidays - vacaciones" (correct), "holidays - grupo", "holidays - quieto"
  - User should submit their answer within 5 seconds
  - User has only 3 wrong attempts
  - If user don't make choice within the timeframe - apps consider the answer as 'Match'
  - User wins if they find matches for 10 original words with less than 3 wrong attempts  
- Use RxJava to implement game management and ui state management
- Use animation with linear interpolation to display falling word

### Decisions made because of restricted time
- Don't build complex ui and animations
- Sacrifice architecture detalization

### What would be the first thing to improve or add if there had been more time
- Replace multiple observers in GameManager with state management
- Add pause, restart and stop of the game
- Improving of ui (different visual feedback of user's choice, attempts number, etc)

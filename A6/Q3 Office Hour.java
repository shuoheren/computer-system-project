Initializaiton:
    int[] ticket = new int[3]; // Initialize each ticket to 0;
    Semaphore Professor = 3; // limit the number of professors to 3;
    Semaphore[] Question = new Semaphore[3]; // Initialize each semaphore Question to 0;
    Semaphore[] Answer = new Semaphore[3]; // Initialize each semaphore Answer to 0;
    Semaphore mutex = 1; // ensure mutual exclusion and initialize to 1.
    
Professor:
    repeat forever {
         signal(Professor);
         wait(mutex);
         wait(Question[id]);
         // answer question
         signal(Answer[id]);
         signal(mutex);
         ticket[id] = 0;
    }
    
Student:
    repeat forever {
         wait(Professor);
         wait(mutex);
         int k = 0;
         for (int j = 0; j < 3; j++) {
             while (ticket[j]!=0) {};
             if (ticket[j]==0) {
                 ticket[j]=1;
                 k = j;
             }
         }
         // ask question;
         signal(mutex);
         signal(Question[k]);
         wait(Answer[k]);
    }
    
    

    
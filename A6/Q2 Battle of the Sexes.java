 Initialization:
     Semaphore turn = 1;  // avoid starvation / monopolization
     Semaphore empty = 1; // signal the fact that the restroom is empty
     Semaphore male = 1; // signal the fact that the male leaves the restroom
     Semaphore female = 1; // signal the fact that the female leaves the restroom
     Semaphore K_male = M; // make sure that the restroom accommodate up to M males
     Semaphore K_female = F; // make sure that the restroom accomodate up to F females
     int N_male = 0; // track the number of males in the restroom
     int N_female = 0; // track the number of females in the restroom
     Semaphore custodian = 1; // signal the fact that the custodian is ready to clean the restroom
     int employees = 0; // track the number of employees used the restroom
    
Male Employees:
    repeat forever {
         wait(turn);
         wait(male);
         // head to the restroom
         employees++;
         N_male++;
         if (N_male==1) {
             wait(empty);
         }
         signal(male);
         signal(turn);
         wait(K_male);
         if (employees==C) {
             signal(custodian);
         }
         // enter restroom
         // use restroom
         signal(K_male);
         wait(male);
         // leave restroom
         N_male--;
         if (N_male==0) {
             signal(empty);
         }
         signal(male);
    }

    
Female Employees:
    repeat forever {
         wait(turn);
         wait(female);
         // head to the restroom
         employees++;
         N_female++;
         if (N_female==1) {
             wait(empty);
         }
         signal(female);
         signal(turn);
         wait(K_female);
         if (employees==C) {
             signal(custodian);
         }
         // enter restroom
         // use restroom
         signal(K_female);
         wait(female);
         // leave restroom
         N_female--;
         if (N_female==0) {
             signal(empty);
         }
         signal(female);
    }


Custodian:
    repeat forever {
         wait(custodian);
         // head to restroom
         wait(empty);
         // clean restroom
         signal(empty);
         employees = 0;
         // leave restroom
    }
         
         
         
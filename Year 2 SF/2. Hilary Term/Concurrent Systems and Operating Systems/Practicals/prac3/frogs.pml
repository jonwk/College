// EMPTY 3, FROG1@1, FROG2@2, FROG3@4, FROG4@5
// START FROG 4 AT 5 GOING LEFT
// START FROG 3 AT 4 GOING LEFT
// START FROG 2 AT 3 GOING LEFT
// MOVE FROG2 FROM 3 TO 2
// EMPTY 3, FROG1@1, FROG2@2, FROG3@4, FROG4@5
// START FROG 1 AT 1 GOING RIGHT
// MOVE FROG1 FROM 1 TO 3
// EMPTY 1, FROG1@3, FROG2@2, FROG3@4, FROG4@5
// ...
// EMPTY 4, FROG1@5, FROG2@1, FROG3@2, FROG4@3
// DONE!

int pods[5];


inline PrintStatus(){
    printf("\nEMPTY %d, FROG1@%d, FROG2@%d, FROG3@%d, FROG4@%d",pods[0],pods[1],pods[2],pods[3],pods[4]);
}


proctype MoveFrog(int i){
    int Empty;
    int center = 3;

    bool right = (i < center) ;
    bool left = (i >= center) ;

    int AlternativeFrogs = 2;
    int SameFrogs = 1;

    if
    ::(left) -> printf("\nSTART FROG %d AT %d GOING LEFT",i,pods[i]);
    ::(right) -> printf("\nSTART FROG %d AT %d GOING RIGHT",i,pods[i]);
    fi;

    do
    ::atomic {
        if
        ::( (left) && ((pods[i]-pods[0]==AlternativeFrogs)||(pods[i]-pods[0]==SameFrogs))) ->
            Empty = pods[0];
            pods[0]=pods[i];
            pods[i]=Empty;
            printf("\nMOVE FROG%d FROM %d TO %d",i,pods[0],pods[i]);
            PrintStatus();
        ::( (right) &&((pods[0]-pods[i]==AlternativeFrogs)||(pods[0]-pods[i]==SameFrogs))) ->
            Empty = pods[0];
            pods[0]=pods[i];
            pods[i]=Empty;
            printf("\nMOVE FROG%d FROM %d TO %d",i,pods[0],pods[i]);
            PrintStatus();
        fi; 
    }
    ::atomic {
        (pods[1]+pods[2]==(4+5) && pods[3]+pods[4]==(1+2)) 
        break;
    }
    od;
}

// proctype swap(int a, int b, int temp){
//     temp = a;
//     a = b;
//     b = temp;
// }

init {
    pods[1]= 1; // BlueFrog 1
    pods[2]= 2; // BlueFrog 2
    pods[0]= 3; // Blank Space
    pods[3]= 4; // RedFrog 1
    pods[4]= 5; // RedFrog 2  
    PrintStatus();
    run MoveFrog(1);
    run MoveFrog(2);
    run MoveFrog(3);
    run MoveFrog(4);
    (_nr_pr==1)
    printf("\nDONE !\n");
    assert(false);
}
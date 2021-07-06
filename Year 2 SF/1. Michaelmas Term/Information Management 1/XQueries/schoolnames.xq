let $c := doc("university.xml")/university/school
return 
string-join($c/SchoolName, " + ")

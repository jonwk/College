<firstnames>
{
 let $c := doc("university.xml")/university/school/headofschool
return 
string-join($c/firstname[1], "+")
}
</firstnames> 
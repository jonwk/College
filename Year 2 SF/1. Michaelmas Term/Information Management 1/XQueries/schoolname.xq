<SchoolName>{
   for $p in doc("university.xml")/university/school
     for $j in $p[@code="MED"]/headofschool
  return <SchoolHead>
          {$j}
        </SchoolHead>
   
}</SchoolName>



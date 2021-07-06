<SchoolName>{
  for $i in doc("university.xml")/university/school[@code="MED"]/headofschool
      return <SchoolHead>{$i}</SchoolHead>
}</SchoolName>
<formats>
{
  for $c in doc("university.xml")/university/school
  return data($c/@format)
  
}
</formats> 
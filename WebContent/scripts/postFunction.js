function showConversionSuccess() {
  // Temporarily highlight Celsius and Kelvin fields
  new Effect.Highlight('c'); 
  new Effect.Highlight('k'); 

  // Turn on successMessage div, then fade it out.
  Element.show('successMessage');
  setTimeout("Effect.DropOut('successMessage');", 2000);
}
underscore.java
===============

The goal of this project is to attempt to produce a java library that resembles the underscore library as closely as possible.  Obviously java doesn't have many of the tools one might want like first class functions, but those limitations will hopefully just make this implementation more spicy.

A few caveats: The goal of this library is to look very javascripty, so method, and class names do not conform to the generally accepted principles.  Further, the interfaces for transform, match, etc, are simply _t for transform and _m for match.  The method you are passing this function into is already named, so having an interface named Function ala Guava just seems like overkill.

Open Questions:
 * Should I depend on any third party libraries like Guava or commons collections?
 * Should I support bean property names property retreival to more completely comply with underscores javascript api (though it would be slower than just putting together a function)

 Working:
 * Many of the collections functions have an implementation
 * Some parts of chaining will work.

 TODO: 
 * Design, right now the function interfaces don't provide context, list or index, I'm not sure if they are necessary, but they should be availble even if you don't want to use them.  I'm thinking that the _f will become an interface with 4 arguments on call, then there will be a decendant abstract class with the current arguments on call.


Thanks
Special thanks to Underscore for giving me a good idea for some excuse to put my code on the web.

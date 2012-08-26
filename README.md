underscore.java
===============

The goal of this project is to attempt to produce a java library that resembles the underscore library as closely as possible.  Obviously java doesn't have many of the tools one might want like first class functions, but those limitations will hopefully just make this implementation more spicy.

A few caveats: The goal of this library is to look very javascripty, so method, and class names do not conform to the generally accepted principles.  Further, the interfaces for transform, match, etc, are simply _t for transform and _m for match.  The method you are passing this function into is already named, so having an interface named Function ala Guava just seems overkill.

Task:
> Provided with a list of ideal (normalized) job titles, create a class that implements a process
that returns the best match when provided with an input string.
> 
> Concretely, given a normalized job titles list of “Architect", "Software engineer", "Quantity
surveyor", and "Accountant", write a process that returns the normalized result for the input.
> 
> input > normalized:
> * "Java engineer" > "Software engineer"
> * "C# engineer" > "Software engineer"
> * "Accountant" > "Accountant"
> * "Chief Accountant" > "Accountant"
> 
> Hint: internally in the process, consider a quality score q, where 0.0 <= q <= 1.0, to find the
closest match.
> 
> Here is some sample code that would call your code
> 
> ```
> String jt = "Java engineer";
> Normaliser n = new Normaliser();
> String normalisedTitle = n.normalise(jt);
> //output normalisedTitle
> jt = "C# engineer";
> normalisedTitle = n.normalise(jt);
> //output normalisedTitle
> jt = "Chief Accountant";
> normalisedTitle = n.normalise(jt);
> //output normalisedTitle
> ```

I want to split this task up, so that a Normaliser is composed of:
* A collection of normal forms - start with a plain immutable List<String> and revise if needed
* A Sanitiser, which cleans an input before it is processed
  * All forms of whitespace standardised to Space; duplicate whitespace removed
  * A real system might do this somewhere else in a data pipeline, but it's arguably a valid part of a normalisation process and the task asks for defensive coding so that can be the approach.
* A Classifier, which takes an input string and the collection of normal forms and produces a ranked set of probabilities, excluding any with a non-zero probability for brevity. Initial options:
  * StringTokenClassifier - breaks the input string into space-delimited tokens and each normal form too, and then does some substring token cross-matching. Algorithm TBC.
    * Internally this probably wants a function to apply the algorithm to an input string/normal string pair.
  * JEVChoiceClassifier - candidate for a bit of AI ranking just for fun.
    * I don't have a TypeSafe account or API key so I'm just going to mock this out by way of example.
* A Selector, which accepts a ranked set of probabilities and applies some choice algorithm over all the *non-zero* probabilities. Initial options:
  * ThresholdSelector - constructed with a probability threshold and returns the highest-probability possibility above the threshold, or invokes the given Fallback
  * PluralitySelector - whichever has the highest probability without a threshold
  * OutlierSelector - whichever has the highest probability, if its probability is sufficiently higher than the next most probable (configured by constructor parameter), or invokes the given Fallback
* A Fallback, which defines the default to return if no match is produced by applying the Selector to the Classifier output. Initial Fallback implementations:
  * NullFallback - returns null and leaves the handling to the caller
  * IdentityFallback - returns the input string

Each concrete implementation should have unit tests, but I want to get the skeleton in place first and make sure the structure is sound.

N.B. some of these calculations would be vulnerable to floating point problems - e.g. 0.3d - 0.1d is calculated as less than 0.2d
 - BigDecimal would be an alternative to handle this, but I'm going to stick with the basic double for now
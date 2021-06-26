# Checking Jumping Frogs



## Simulating Frogs

```
spin frogs.pml
```

If you want all outputs left-aligned, use:

```
spin -T frogs.pml
```

## Verifying Frogs

### "Default" Verification
```
spin -run frogs.pml
```

Always finds one of deadlocking states.

### Displaying/Re-playing the "trail"

```
spin -t frogs.pml
```

Again, you can add the option `-T` to align the output.

### Ignoring Deadlock

```
spin -run -E frogs.pml
```

However, if we run this with a simple correct model, we find no errors.
The only "default" errors in the frog model are deadlocks.

## Finding a Solution

Simulation is very unlikely to show a correct solution. Default verification can only find the deadlocks. How do we use the model to generate a possible solution?

### Hint

Consider some assertion `done` that would be true if and only if the frogs had managed
to change places as required. Add the line `assert(!done)` at end of the model. 
Then a deadlock-ignoring verification run will find a solution because:
it does an exhaustive search of all possible executions,
so it will find the sequence that leads to `done` actually being true,
leading to the line `assert(!done)` to signal a verification error.
The trail file will contain the sequence that lead to `done` being true,
making that assertion fail. Replaying that trail file gives a solution.




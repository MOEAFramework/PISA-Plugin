# Troubleshooting

### Assertion failed: fp != NULL, file \<filename\>, line \<linenumber\>

PISA modules communicate using the file system.  Some anti-virus software scans the contents of files before read and
after write operations.  This may cause one of the PISA communication files to become inaccessible and cause this error.
To test if this is the cause, try disabling your anti-virus and re-run the program.
  
A more permanent and secure solution involves adding an exception to the anti-virus software to prevent active
monitoring of PISA communication files.  For example, first add the line

```
java.io.tmpdir=<folder>
```

to `moeaframework.properties` and set `<folder>` to some temporary folder where the PISA communication files
will be stored.  Then configure your anti-virus software to ignore the contents of `<folder>`.

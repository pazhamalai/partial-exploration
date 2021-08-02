package de.tum.in.pet.util;

import de.tum.in.pet.implementation.reachability.UpdateMethod;
import de.tum.in.pet.sampler.SuccessorHeuristic;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import de.tum.in.probmodels.explorer.InformationLevel;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.checkerframework.checker.nullness.Opt;

@SuppressWarnings("PMD.SystemPrintln")
public final class CliHelper {
  private static final Logger logger = Logger.getLogger(CliHelper.class.getName());

  private CliHelper() {
    // Empty
  }

  public static Option getDefaultHeuristicOption() {
    return new Option(null, "heuristic", true, "Sampling heuristic to be used.");
  }

  public static Option getDefaultInformationLevelOption() {
    return new Option(null, "informationLevel", true, "Information Level Assumption of Model. Default is WhiteBox.");
  }

  public static SuccessorHeuristic parseHeuristic(String optionString, SuccessorHeuristic defaultValue) {
    if (optionString == null) {
      return defaultValue;
    }
    try {
      return SuccessorHeuristic.valueOf(optionString);
    } catch (IllegalArgumentException e) {
      logger.log(Level.FINE, "Failed to parse heuristic", e);
      String values = Arrays.stream(SuccessorHeuristic.values())
          .map(Object::toString)
          .collect(Collectors.joining(", "));
      System.out.println("Unknown heuristic " + optionString + ". Possible values are: " + values);
      System.exit(1);
      throw new AssertionError(e);
    }
  }

  public static InformationLevel parseInformationLevel(String optionString, InformationLevel defaultValue) {
    if (optionString == null) {
      return defaultValue;
    }
    try {
      return InformationLevel.valueOf(optionString);
    } catch (IllegalArgumentException e) {
      logger.log(Level.FINE, "Failed to parse information level", e);
      String values = Arrays.stream(InformationLevel.values())
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      System.out.println("Unknown information level " + optionString + ". Possible values are: " + values);
      System.exit(1);
      throw new AssertionError(e);
    }
  }

  public static UpdateMethod parseUpdateMethod(String optionString, UpdateMethod defaultValue) {
    if (optionString == null) {
      return defaultValue;
    }
    try {
      return UpdateMethod.valueOf(optionString);
    } catch (IllegalArgumentException e) {
      logger.log(Level.FINE, "Failed to parse information level", e);
      String values = Arrays.stream(InformationLevel.values())
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      System.out.println("Unknown information level " + optionString + ". Possible values are: " + values);
      System.exit(1);
      throw new AssertionError(e);
    }
  }

  public static CommandLine parse(Options options, String[] args) {
    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser cliParser = new DefaultParser();
    try {
      return cliParser.parse(options, args);
    } catch (ParseException e) {
      System.out.println("Failed to parse command line arguments: " + e.getMessage());
      formatter.printHelp("argument list", options);
      System.exit(1);
      throw new AssertionError(e);
    }
  }
}

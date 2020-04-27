# Tasks

CommandLineParser:
ArrayList<CommandLineParseResult> parseCommands(String[] args, ArrayList<CommandLineStrategy>)

interface ParseStrategy<T>
T parseValue(String s)

ParseIntegerStrategy<Integer>
Integer parseValue()


CommandLineStrategy:
MatchingStategy
ParseStrategy<T> (PortStrategy)
SetterStrategy<T1>

MatchingStrategy:
private value

ctor (value)

bool isMatch



// CONFIG

Configuration
Port

ClientConfiguration implements Configuration:

ServerConfiguration implements Configuration:
Host

// 

ConfigurationBuilder
Configuration BuildFromCommandLine<T>(String[] args, ArrayList<CommandLineStrategy>) where T is Configuration {
  for args
    for comstrat
      if arg.isMatch
       comstrat.map(configuration, parseStrategy(arg[i + 1]))
}

ConfigurationMapper<T>:
  void map (Configuration configuration, T value, T default)
---

Tasks:






MessageHandler
  ctor stream
  override handleInput?

ClientMessageHandler
override handleInput

ServerMessageHandler
override handleInput

using runnable
your class can be a subclass of anotehr class


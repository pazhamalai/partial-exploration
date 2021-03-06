# This script runs experiments for InformationLevel BLACKBOX, UpdateMethod BLACKBOX for different variants of
# simulateMec and deltaTMethod options. Each variant is run in a new process.

# The output directory for each variant is named after the first letter of the option provided for InformationLevel,
# UpdateMethod, SimulateMec and deltaTMethod. For example, for the following configuration
# informationLevel BLACKBOX, updateMethod BLACKBOX, simulateMec STANDARD, deltaTMethod P_MIN
# result will be stored in the directory 'BBSP', where first 'B' corresponds to informationLevel, second 'B' corresponds
# to updateMethod, the third 'S' corresponds to simulateMec value and the fourth 'P' corresponds to deltaTMethod.

import os
import inputOptions
import argparse

base_dir = 'experimentResults'
base_command = 'python3 runNExperiments.py --informationLevel BLACKBOX --updateMethod BLACKBOX'

parser = argparse.ArgumentParser()
parser.add_argument(inputOptions.number_of_experiments_option, type=int)
parser.add_argument(inputOptions.ctmdp_benchmarks_option)
arguments = parser.parse_args()
number_of_experiments = arguments.nExperiments
is_ctmdp = arguments.ctmdp


class Configuration:
    def __init__(self, simulate_mec_config, delta_t_config, output_directory_config):
        self.simulate_mec_config = simulate_mec_config
        self.delta_t_config = delta_t_config
        self.output_directory_config = output_directory_config


def run_configuration(configuration):
    simulate_mec_param = inputOptions.simulate_mec_option + ' ' + configuration.simulate_mec_config
    deltat_method_param = inputOptions.deltat_method_option + ' ' + configuration.delta_t_config
    output_directory_param = inputOptions.output_directory_option + ' ' + configuration.output_directory_config
    n_experiments_param = inputOptions.number_of_experiments_option + ' ' + str(number_of_experiments)
    is_ctmdp_param = None

    if is_ctmdp:
        is_ctmdp_param = inputOptions.ctmdp_benchmarks_option
    else:
        is_ctmdp_param = ""
    os.system(
        base_command + ' ' + simulate_mec_param + ' ' + deltat_method_param + ' ' + output_directory_param + ' ' + n_experiments_param + ' ' + is_ctmdp_param)


def configuration_1():
    output_directory = base_dir + '/' + 'BBSP'
    return Configuration(inputOptions.simulate_mec_standard, inputOptions.deltat_method_p_min, output_directory)


def configuration_2():
    output_directory = base_dir + '/' + 'BBHP'
    return Configuration(inputOptions.simulate_mec_heuristic, inputOptions.deltat_method_p_min, output_directory)


def configuration_3():
    output_directory = base_dir + '/' + 'BBCP'
    return Configuration(inputOptions.simulate_mec_cheat, inputOptions.deltat_method_p_min, output_directory)


def configuration_4():
    output_directory = base_dir + '/' + 'BBSM'
    return Configuration(inputOptions.simulate_mec_standard, inputOptions.deltat_method_max_successors,
                         output_directory)


def configuration_5():
    output_directory = base_dir + '/' + 'BBHM'
    return Configuration(inputOptions.simulate_mec_heuristic, inputOptions.deltat_method_max_successors,
                         output_directory)


def configuration_6():
    output_directory = base_dir + '/' + 'BBCM'
    return Configuration(inputOptions.simulate_mec_cheat, inputOptions.deltat_method_max_successors, output_directory)


run_configuration(configuration_2())

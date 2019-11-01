import json
import itertools


def get_task_batches(nodes):
    # Build a map of node names to node instances
    # name_to_instance = dict((n.name, n) for n in nodes)
    name_to_instance = dict((key, value[0]) for key, value in nodes.items())

    # Build a map of node names to dependency names
    # name_to_deps = dict((n.name, set(n.depends)) for n in nodes)
    name_to_deps = dict((value[0], set(value[1:])) for key, value in nodes.items())

    # This is where we'll store the batches
    batches = []

    # While there are dependencies to solve...
    while name_to_deps:

        # Get all nodes with no dependencies
        ready = {name for name, deps in name_to_deps.items() if not deps}

        # If there aren't any, we have a loop in the graph
        if not ready:
            msg = "Circular dependencies found!\n"
            raise ValueError(msg)

        # Remove them from the dependency graph
        for name in ready:
            del name_to_deps[name]
        for deps in name_to_deps.values():
            deps.difference_update(ready)

        # Add the batch to the list
        batches.append({name_to_instance[name] for name in ready})

    # Return the list of batches
    return batches


def data_retriever(file):
    json_data = []
    with open(file) as inputFile:
        workflow = json.load(inputFile)
    task_num = 1
    for task in workflow['workflowTasks']:

        # Creating variables for the task queries
        table_set = set()
        column_set = set()
        param_builder = {}

        # Assign values of methodID, datName and DatOutput.
        method_id, source, destination = task["methodID"], task["datName"], task["datOutput"]

        for dataset in task['avDataSetList']:
            if dataset['tableName']:
                table_set.add(dataset['tableName'])
            if dataset['paramname']:
                column_set.add(dataset['paramname'])

        for parameter in task['taskParams']:
            if parameter['paramName'] and parameter['operator'] and parameter['paramValue']:
                param_builder[parameter['paramName']] = parameter["paramValue"]

        query_builder = 'SELECT ' + ", ".join(column_set) + ' FROM ' + ", ".join(table_set)

        json_data.append({"task": "T"+str(task_num), "method_id": method_id, "source": source,
                          "destination": destination, "query_builder": query_builder, "param_builder": param_builder})
        task_num += 1

    return json_data


def task_identifier(task_list):
    task_dictionary = {}
    if len(task_list) > 1:
        for task1, task2 in itertools.permutations(task_list, 2):
            if task1["destination"] == task2["source"]:

                if task2["task"] not in task_dictionary:
                    task_dictionary[task2["task"]] = [task2["task"]]
                    task_dictionary[task2["task"]].append(task1["task"])
                else:
                    task_dictionary[task2["task"]].append(task1["task"])
            else:
                if task2["task"] in task_dictionary:
                    if task2["task"] not in task_dictionary.get(task2["task"]):
                        task_dictionary[task2["task"]].append(task2["task"])
                else:
                    task_dictionary[task2["task"]] = []
                    task_dictionary[task2["task"]].append(task2["task"])

        return get_task_batches(task_dictionary)
    else:
        return task_list

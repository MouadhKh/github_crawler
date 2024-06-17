import React from "react";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import {styles} from "../styles.ts";
import {DeveloperResponse} from "../types/developer.ts";


type DeveloperProps = {
    developers: DeveloperResponse[];
}
const DevelopersTable: React.FC<DeveloperProps> = ({developers}: DeveloperProps) => {
    return (<TableContainer component={Paper} style={{minHeight: 400, overflow: 'auto'}}>
        <Table stickyHeader>
            <TableHead>
                <TableRow>
                    <TableCell sx={styles.tableHeader}>Username</TableCell>
                    <TableCell sx={styles.tableHeader}>Repositories</TableCell>
                    <TableCell sx={styles.tableHeader}>Languages</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {developers.map((developer, index) => {
                    return (
                        <TableRow key={index}>
                            <TableCell
                                sx={styles.centerText}>{developer.username}</TableCell>
                            <TableCell sx={styles.centerText}>{developer.numberOfRepositories || ''}</TableCell>
                            <TableCell sx={styles.centerText}>{developer.languages.join(', ')}</TableCell>
                        </TableRow>
                    );
                })}
            </TableBody>
        </Table>
    </TableContainer>);
}

export default DevelopersTable;